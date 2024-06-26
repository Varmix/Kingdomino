package kingdomino.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JTextPane;

import kingdomino.supervisers.*;
import kingdomino.swing.viewmodels.*;

public class SwingPlayGameView extends SwingView implements PlayGameView {
	private final ImageIcon background = new ImageIcon("resources/images/play_game_background.jpg");
	private static final long serialVersionUID = -8437344739545669731L;

	private final JTextPane pane;
	private final List<TileViewModel> kingdom = new ArrayList<>();
	private final List<DominoViewModel> actualLine = new ArrayList<>();
	private final List<DominoViewModel> nextLine = new ArrayList<>();
	private Rectangle pieceRect;

	private PlayGameSuperviser superviser;
	private Deque<String> messages =  new ArrayDeque<>();
	private SwingViewMode mode;
	
	public SwingPlayGameView(String name, PlayGameSuperviser superviser) {
		super(name);

		this.superviser = superviser;
		this.superviser.setView(this);
	
		
		int left = Theme.PANEL_WIDTH / 100;
		
		pane = new JTextPane();
		pane.setBounds(left, 608, Theme.PANEL_WIDTH - (Theme.PANEL_WIDTH / 100 * 3), 150);
		pane.setFocusable(false);
		
		mode = new KingdomViewMode();
		
		this.add(pane);
	}

	@Override
	public void onEnter(String fromScreen) {
		superviser.onEnter(fromScreen);
	}

	@Override
	public void onLeave(String toScreen) {
		superviser.onLeave(toScreen);
	}

	@Override
	public void startDraw() {
		this.actualLine.clear();
		this.nextLine.clear();
		this.kingdom.clear();
	}

	@Override
	public void addToNextLine(String terrain1, int crownsCount1, String terrain2, int crownsCount2, String color) {
		nextLine.add(new DominoViewModel(getTile(terrain1), crownsCount1, getTile(terrain2), crownsCount2,
				new Color(Integer.parseInt(color.substring(1), 16))));

	}

	@Override
	public void addToActualLine(String terrain1, int crownsCount1, String terrain2, int crownsCount2, String color) {
		actualLine.add(new DominoViewModel(getTile(terrain1), crownsCount1, getTile(terrain2), crownsCount2,
				new Color(Integer.parseInt(color.substring(1), 16))));

	}

	@Override
	public void addToKingdom(String t, int crownsCount, int row, int col) {
		int left = Theme.PANEL_WIDTH / 100 * 2;
		int top = Theme.PANEL_HEIGHT / 100 * 2;
		int tileSize = Theme.TILE_SIZE;

		kingdom.add(new TileViewModel(getTile(t), crownsCount, top + col * tileSize, left + row * tileSize));
	}

	@Override
	public void addToKingdom(String t, int crownsCount, int row, int col, String hexColor) {
		int left = Theme.PANEL_WIDTH / 100 * 2;
		int top = Theme.PANEL_HEIGHT / 100 * 2;
		int tileSize = Theme.TILE_SIZE;

		kingdom.add(new TileViewModel(getTile(t), crownsCount, top + col * tileSize, left + row * tileSize, hexColor));
	}

	private ImageIcon getTile(String t) {
		return Theme.TILES.get(t);
	}

	@Override
	public void endDraw() {
		this.repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, Theme.PANEL_WIDTH, Theme.PANEL_HEIGHT, null);
		
		drawCommands(g);
		
		drawKingdom(g);
		drawCurrentPos(g);
		drawNextLine(g);
		drawActualLine(g);
	}

	private void drawCurrentPos(Graphics g) {
		if (pieceRect != null) {
			g.drawRect(pieceRect.x, pieceRect.y, pieceRect.width, pieceRect.height);
		}

	}

	private void drawKingdom(Graphics g) {
		for (var tvm : this.kingdom) {
			g.drawImage(tvm.getImage(), tvm.getX(), tvm.getY(), null);
			g.setColor(tvm.getBorderColor());
			g.drawRect(tvm.getX(), tvm.getY(), Theme.TILE_SIZE, Theme.TILE_SIZE);

			if (tvm.getCrownsCount() > 0) {
				g.setColor(Color.WHITE);
				g.drawString(Integer.toString(tvm.getCrownsCount()), tvm.getX() + Theme.TILE_SIZE - 8, tvm.getY()+8);
			}
		}
	}

	private void drawNextLine(Graphics g) {
		int left = Theme.PANEL_WIDTH / 2 + Theme.PANEL_WIDTH / 100 * 2;
		int top = Theme.PANEL_HEIGHT / 100 * 4;
		int tileSize = Theme.TILE_SIZE;

		g.setColor(Theme.PRIMARY_COLOR);
		g.drawString("NEXT LINE", left, top - Theme.PANEL_WIDTH / 100);

		for (var dvm : this.nextLine) {
			if(dvm.hasColor()) {
				g.setColor(dvm.getColor());
				g.fillRect(left - 4, top - 4, 2 * tileSize + 8, tileSize + 8);
			}
			drawTile(g, left, top, tileSize, dvm);
			if(dvm.hasColor()) {
				g.setColor(new Color(dvm.getColor().getRed(), dvm.getColor().getGreen(), dvm.getColor().getBlue(), 128));
				g.fillRect(left - 2, top - 2, 2 * tileSize + 4, tileSize + 4);
			}
			left += (2 * tileSize) + 5;
		}
	}

	private void drawActualLine(Graphics g) {
		int tileSize = Theme.TILE_SIZE;
		int left = Theme.PANEL_WIDTH / 2 + Theme.PANEL_WIDTH / 100 * 2;
		int top = Theme.PANEL_HEIGHT / 100 * 8 + tileSize;

		g.setColor(Theme.PRIMARY_COLOR);
		g.drawString("ACTUAL LINE", left, top - Theme.PANEL_WIDTH / 100);

		for (var dvm : this.actualLine) {
			g.setColor(dvm.getColor());
			g.fillRect(left - 2, top - 2, 2 * tileSize + 4, tileSize + 4);

			drawTile(g, left, top, tileSize, dvm);

			left += (2 * tileSize) + 5;
		}
	}

	private void drawTile(Graphics g, int left, int top, int tileSize, DominoViewModel dvm) {
		g.drawImage(dvm.getImage(0), left, top, null);
		if (dvm.getCrowsCount(0) > 0) {
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString(dvm.getCrowsCount(0)), left + 1 * tileSize - 8, top + 8);
		}

		g.drawImage(dvm.getImage(1), left + tileSize, top, null);
		if (dvm.getCrowsCount(1) > 0) {
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString(dvm.getCrowsCount(1)), left + 2 * tileSize - 8, top + 8);
		}
	}
	
	@Override
	public void switchMode(ViewMode mode) {
		if(ViewMode.KINGDOM == mode && !(this.mode instanceof KingdomViewMode) ) {
			this.mode = new KingdomViewMode();
		} else if(ViewMode.LINES == mode && !(this.mode instanceof LineViewMode)){
			this.mode = new LineViewMode();
		}
	}
	
	@Override
	public void onKeyTyped(int keyCode) {
		this.mode.onKeyTyped(keyCode, superviser);
	}

	private void drawCommands(Graphics g) {
		this.mode.drawCommands(g);
	}
	
	@Override
	public void addMessage(String msg) {
		this.messages.push(msg);
		this.pane.setText(messages.stream().collect(Collectors.joining("\n")));
		
	}
	
}

interface SwingViewMode {
	void drawCommands(Graphics g);
	void onKeyTyped(int keyCode, PlayGameSuperviser superviser);
}

class KingdomViewMode implements SwingViewMode {

	@Override
	public void drawCommands(Graphics g) {
		int left = Theme.PANEL_WIDTH / 2 + Theme.PANEL_WIDTH / 100 * 2;
		int top = Theme.PANEL_HEIGHT / 100 * 33;
		int width = Theme.TILE_SIZE*8 + 40;
		
		g.setColor(new Color(255,255,255,226));
		g.fillRect(left-2, top-11, width, 138);
		g.setColor(Color.BLACK);
		
		g.drawString("\u2191 : déplacer le domino vers le haut",left, top);
		g.drawString("\u2193 : déplacer le domino vers le bas",left, top+20);
		g.drawString("\u2192 : déplacer le domino vers la droite",left, top+40);
		g.drawString("\u2190 : déplacer le domino vers la gauche",left, top+60);
		g.drawString("Espace : pivoter le domino de 90°",left, top+80);
		g.drawString("\u23ce : confirmer le placement",left, top+100);
		g.drawString("Esc: passer son tour", left, top+120);	
	}

	@Override
	public void onKeyTyped(int keyCode, PlayGameSuperviser superviser) {
		if (keyCode == KeyEvent.VK_DOWN) {
			superviser.onMove(1, 0);
		} else if (keyCode == KeyEvent.VK_UP) {
			superviser.onMove(-1, 0);
		} else if (keyCode == KeyEvent.VK_LEFT) {
			superviser.onMove(0, -1);
		} else if (keyCode == KeyEvent.VK_RIGHT) {
			superviser.onMove(0, 1);
		} else if (keyCode == KeyEvent.VK_SPACE) {
			superviser.onRotate();
		} else if(keyCode == KeyEvent.VK_ENTER) {
			superviser.onConfirm();
		} else if(keyCode == KeyEvent.VK_ESCAPE) {
			superviser.onPass();
		}
	}
}

class LineViewMode implements SwingViewMode {

	@Override
	public void drawCommands(Graphics g) {
		int left = Theme.PANEL_WIDTH / 2 + Theme.PANEL_WIDTH / 100 * 2;
		int top = Theme.PANEL_HEIGHT / 100 * 33;
		int width = Theme.TILE_SIZE*8 + 40;
		
		g.setColor(new Color(255,255,255,226));
		g.fillRect(left-2, top-11, width, 22);
		g.setColor(Color.BLACK);
		
		g.drawString("Espace : choisir le domino suivant",left, top);
		g.drawString("\u23ce : confirmer le domino suivant",left, top+20);
		
	}

	@Override
	public void onKeyTyped(int keyCode, PlayGameSuperviser superviser) {
		if(keyCode == KeyEvent.VK_ENTER) {
			superviser.onPieceSelected();
		} else if(keyCode == KeyEvent.VK_SPACE) {
			superviser.onSelectNextPiece();
		}
	}
	
}
