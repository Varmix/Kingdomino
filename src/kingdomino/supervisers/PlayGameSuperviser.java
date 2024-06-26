package kingdomino.supervisers;

import kingdomino.domains.*;
import kingdomino.supervisers.PlayGameView.ViewMode;

public class PlayGameSuperviser extends Superviser {
	private final KingDominoGameProvider provider;
	private KingDominoGame game;
	private PlayGameView view;
	private Coordinate[] tilesPos = new Coordinate[] { Coordinate.ofRowCol(0, 0), Coordinate.ofRowCol(0, 1) };

	public PlayGameSuperviser(KingDominoGameProvider factory) {
		this.provider = factory;

	}

	public void setView(PlayGameView view) {
		this.view = view;
	}

	/*
	 * [ALGO]Postconditions: soit Q = 12 = nombre de dominos total / 4 Pile de
	 * dominos: le nombre de dominos de la pile = Q x nombre de joueurs - 2 x nombre
	 * de rois en jeu (car 2 ligne de tirages deja remplies) Ligne de tirage
	 * actuelle: nombre de dominos de la ligne de tirage = nombre de rois en jeu (4
	 * si nombre de joueurs pair, 3 sinon) ET dominos tries en ordre croissant de
	 * leur numéro (ET face paysage des dominos visible) ET dominos issus de la pile
	 * (ce qui singifie qu'ils n'y sont plus) ET un joueur est attribue a chaque
	 * domino Ligne de tirage suivante: nombre de dominos de la ligne de tirage =
	 * nombre de rois en jeu (4 si nombre de joueurs pair, 3 sinon) ET dominos tries
	 * en ordre croissant de leur numero (ET face paysage des dominos visible) ET
	 * dominos issus de la pile (ce qui singifie qu'ils n'y sont plus) Royaume:
	 * royaume vide et de capacité 5
	 * 
	 * [ALGO]CTT de l'operation generant une ligne de tirage
	 * 
	 */
	@Override
	public void onEnter(String fromScreen) {
		if ("MainMenu".equals(fromScreen)) {
			game = provider.getLastGame();
			game.start();
			view.addMessage("Le joueur " + game.getCurrentPlayer().getName() + " a la main");
			draw();
		}
	}

	private void draw() {
		view.startDraw();

		drawKingdom(game.getCurrentPlayer());
		drawDominoOnKingdom(game.getCurrentDomino(), game.getCurrentPlayer());
		drawNextLine(game.getNextLine(), game.getCurrentPlayer());
		drawActualLine(game.getCurrentLine());
		view.endDraw();
	}


	

	private void drawKingdom(Player player) {
		var kingdom = player.getKingdom();
		var playableTiles = player.getKingdom().playableTiles(game.getCurrentDomino());
		for (var c : kingdom) {
			var t = kingdom.getTileAt(c);
			view.addToKingdom(t.getTerrainAsString(), t.getCrownsCount(), c.getRow(), c.getCol());
		}
		for (var d : playableTiles) {
			var t = kingdom.getTileAt(d);
			if(kingdom.isValid(game.getCurrentDomino(), d, d)) {
				view.addToKingdom(t.getTerrainAsString(), t.getCrownsCount(), d.getRow(), d.getCol());
			}
		}
	}

	private void drawDominoOnKingdom(Domino domino, Player player) {
		view.addToKingdom(domino.getTerrain(0).toString(), domino.getCrownsCount(0), tilesPos[0].getRow(),
				tilesPos[0].getCol(), player.getHexColor());
		view.addToKingdom(domino.getTerrain(1).toString(), domino.getCrownsCount(1), tilesPos[1].getRow(),
				tilesPos[1].getCol(), player.getHexColor());
	}

	private void drawActualLine(DrawLine actualLine) {
		for (Domino d : actualLine) {
			view.addToActualLine(d.getTerrain(0).toString(), d.getCrownsCount(0), d.getTerrain(1).toString(),
					d.getCrownsCount(1), actualLine.getColorFor(d));
		}
	}

	private void drawNextLine(DrawLine nextLine, Player currentPlayer) {
		for (Domino d : nextLine) {
			if (game.isNextDomino(d)) {
				view.addToNextLine(d.getTerrain(0).toString(), d.getCrownsCount(0), d.getTerrain(1).toString(),
						d.getCrownsCount(1), currentPlayer.getHexColor());
			} else {
				view.addToNextLine(d.getTerrain(0).toString(), d.getCrownsCount(0), d.getTerrain(1).toString(),
						d.getCrownsCount(1), nextLine.getColorFor(d));
			}
		}
	}
	


	public void onMove(int dr, int dc) {
		tilesPos[0] = tilesPos[0].translate(dr, dc);
		tilesPos[1] = tilesPos[1].translate(dr, dc);

		draw();
	}

	public void onRotate() {
		tilesPos[1] = tilesPos[1].rotate90From(tilesPos[0]);
		draw();
	}

	public void onConfirm() {
		switch (game.setCurrentDominoAt(tilesPos[0], tilesPos[1])) {
		case INVALID:
			view.addMessage("Vous ne pouvez pas placer le domino à cet endroit.");
			break;
		case NEXT_PLAYER:
			tilesPos[0] = Coordinate.ofRowCol(0, 0);
			tilesPos[1] = Coordinate.ofRowCol(0, 1);
			break;
		case SELECT_NEXT_DOMINO:
			view.addMessage("Choisissez le domino suivant.");
			view.switchMode(ViewMode.LINES);
			break;
		case GAME_OVER:
			view.goTo("GameOver");
			break;
		}
		draw();
	}

	public void onPass() {
		switch (game.skip()) {
		case NEXT_PLAYER:
			tilesPos[0] = Coordinate.ofRowCol(0, 0);
			tilesPos[1] = Coordinate.ofRowCol(0, 1);
			break;
		case SELECT_NEXT_DOMINO:
			view.addMessage("Choisissez le domino suivant.");
			view.switchMode(ViewMode.LINES);
			break;
		case GAME_OVER:
			view.goTo("GameOver");
			break;
		default:
			break;
		}
		draw();
	}

	public void onSelectNextPiece() {
		game.moveToNextSelectableDomino();
		draw();
	}

	public void onPieceSelected() {
		game.setNextDomino();
		tilesPos[0] = Coordinate.ofRowCol(0, 0);
		tilesPos[1] = Coordinate.ofRowCol(0, 1);

		view.addMessage("Le joueur " + game.getCurrentPlayer().getName() + " a la main");
		view.switchMode(ViewMode.KINGDOM);
		
		draw();
	}

}
