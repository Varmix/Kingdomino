package kingdomino.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.*;

import kingdomino.supervisers.*;

public final class SwingMainMenuView extends SwingView implements MainMenuView {
	private static final long serialVersionUID = -2211970715714357966L;
	private final ImageIcon background = new ImageIcon("resources/images/main_menu_background.jpg");
	private final MainMenuSuperviser superviser;
	
	private List<String> items;	
	private int selected = 0;
	
	public SwingMainMenuView(String title, MainMenuSuperviser superviser) {
		super(title);
		this.superviser = superviser;
		this.superviser.setView(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);	
		drawBackground(g);
		drawCommands(g);
		drawMenuItems(g);
	}

	private void drawCommands(Graphics g) {
		int left = Theme.PANEL_WIDTH/100*2;
		int top = Theme.PANEL_HEIGHT/100*3;
		
		g.setColor(new Color(255,255,255,128));
		g.fillRect(left-5, top-11, 200, 62);
		g.setColor(Color.BLACK);
		
		g.drawString("\u2191 : déplacer vers le haut",left, top);
		g.drawString("\u2193 : déplacer vers le bas",left, top+20);
		g.drawString("\u23ce : choisir",left, top+40);
		
	}

	private void drawBackground(Graphics g) {
		g.drawImage(background.getImage(), 0, 0, null);
	}
	
	private void drawMenuItems(Graphics g) {
		int left = Theme.PANEL_WIDTH/3;
		int top = Theme.PANEL_HEIGHT/3;
		int width = Theme.PANEL_WIDTH/3;
		int height = Theme.PANEL_HEIGHT/12;
		int gap = Theme.PANEL_HEIGHT/15;
		
		
		for(int i=0; i < items.size(); ++i) {
			var item = items.get(i);
			
			if(i == selected) {
				g.setColor(Theme.SECONDARY_BACKGROUND_COLOR_ALPHA);
				g.fillRoundRect(left - gap/2, top - gap/2, width + gap, height + gap, gap, gap);
				
				g.setColor(Theme.SECONDARY_BACKGROUND_COLOR);
				g.drawRoundRect(left - gap/2, top - gap/2, width + gap, height + gap, gap, gap);
			}
			
			g.setColor(Theme.SECONDARY_BACKGROUND_COLOR);
			g.fillRoundRect(left, top, width, height, 5, 5);
			
			g.setFont(Theme.ITEM_FONT);
			g.setColor(Theme.PRIMARY_COLOR);
			g.drawString(item, left+gap/2, top+height/2);		
			
			top += gap + height;
		}
		
	}

	@Override
	public void onKeyTyped(int keyCode) {
		if(keyCode == KeyEvent.VK_DOWN) {
			this.selected = (selected + 1) % items.size();
		} else if(keyCode == KeyEvent.VK_UP) {
			this.selected = (selected == 0) ? items.size() - 1 : selected - 1;
		} else if(KeyEvent.VK_ENTER == keyCode) {
			this.superviser.onItemSelected(this.selected);
		}
		
		this.repaint();
	}

	@Override
	public void setItems(List<String> items) {
		this.items = List.copyOf(items);
		this.selected = 0;
		this.repaint();
	}

}
