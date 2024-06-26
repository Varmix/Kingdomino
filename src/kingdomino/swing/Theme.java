package kingdomino.swing;

import java.awt.*;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.UIManager;

/**
 * Définit l'aspect général des écrans.
 * */
final class Theme {
	static {
		 try {
			UIManager.setLookAndFeel(
			            UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Dimensions
	public static final int WINDOW_WIDTH = 1200;
	public static final int WINDOW_HEIGHT = 800;
	public static final int PANEL_WIDTH = 1184;
	public static final int PANEL_HEIGHT = 760;
	public static final int TILE_SIZE = 64;
	
	//Police de caractères
	public static final Font ITEM_FONT = new Font("Ravie", Font.PLAIN, 24);
	public static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.PLAIN, 20);
	public static final Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
	
	//Couleurs
	public static final Color PRIMARY_BACKGROUND_COLOR = new Color(234, 179, 16);
	public static final Color PRIMARY_BACKGROUND_COLOR_ALPHA =  new Color(234, 179, 16, 158);
	public static final Color SECONDARY_BACKGROUND_COLOR = new Color(5, 111, 150);
	public static final Color SECONDARY_BACKGROUND_COLOR_ALPHA =  new Color(5, 111, 150, 158);
	
	public static final Color PRIMARY_COLOR = new Color(253, 245, 223);
	
	public static final Map<String, ImageIcon> TILES = Map.of(
			"empty", new ImageIcon("resources/images/empty.jpg"), 
			"castle",new ImageIcon("resources/images/castle.jpg"), 
			"field", new ImageIcon("resources/images/field.jpg"),
			"forest", new ImageIcon("resources/images/forest.jpg"), 
			"grasslands", new ImageIcon("resources/images/grasslands.jpg"), 
			"lake", new ImageIcon("resources/images/lake.jpg"),
			"mine", new ImageIcon("resources/images/mine.jpg"), 
			"swamp", new ImageIcon("resources/images/swamp.jpg")
	);


}
