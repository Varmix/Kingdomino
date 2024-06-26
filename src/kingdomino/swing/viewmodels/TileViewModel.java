package kingdomino.swing.viewmodels;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;

public final class TileViewModel {

	private final ImageIcon image;
	private final int y;
	private final int x;
	private final int crownsCount;
	private Color color;

	public TileViewModel(ImageIcon image, int crownsCount, int x, int y) {
		this(image, crownsCount, x, y, "");
	}

	public TileViewModel(ImageIcon image, int crownsCount, int x, int y, String hexColor) {
		this.image = image;
		this.crownsCount = crownsCount;
		this.y = y;
		this.x = x;
		this.color = hexColor.isEmpty() ? Color.BLACK : new Color(Integer.parseInt(hexColor.substring(1), 16));
	}

	public Image getImage() {
		return image.getImage();
	}

	public int getX() {
		return  x;
	}

	public int getY() {
		return  y;
	}

	public int getCrownsCount() {
		return crownsCount;
	}
	
	public Color getBorderColor() {
		return color;
		
	}

}
