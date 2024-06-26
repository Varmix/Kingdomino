package kingdomino.swing;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class SwingScoreView extends JPanel {
	private static final long serialVersionUID = -7033330803874528386L;
	private JLabel playerNameLabel;
	private JLabel scoreLabel;
	
	public SwingScoreView(String playerName, int score, String color) {
		this.setLayout(new BorderLayout(16, 16));
		this.setBackground(getBackgroundColor(color));
		
		playerNameLabel= new JLabel(playerName, SwingConstants.CENTER);	
		playerNameLabel.setForeground(Theme.PRIMARY_COLOR);
		playerNameLabel.setFont(Theme.ITEM_FONT);
		
		scoreLabel = new JLabel(String.format("%02d", score), SwingConstants.CENTER);
		scoreLabel.setForeground(Theme.PRIMARY_COLOR);
		scoreLabel.setFont(Theme.ITEM_FONT);
		scoreLabel.setFont(Theme.ITEM_FONT.deriveFont(Theme.ITEM_FONT.getSize2D()*2.0f));
		
		add(playerNameLabel, BorderLayout.PAGE_START);
		add(scoreLabel,BorderLayout.CENTER);
		
		setBorder(new LineBorder(getBorderColor(color), 4, true));
	}

	private Color getBackgroundColor(String color) {
		String rgbHex = color == null || !color.startsWith("#") ? "#ffffff" : color;
		Color rgb = new Color(Integer.parseInt(rgbHex.substring(1), 16));
		return new Color(rgb.getRed(), rgb.getGreen(), rgb.getBlue(), 200);
	}
	
	private Color getBorderColor(String color) {
		String rgbHex = color == null || !color.startsWith("#") ? "#ffffff" : color;
		return new Color(Integer.parseInt(rgbHex.substring(1), 16));
	}
}
