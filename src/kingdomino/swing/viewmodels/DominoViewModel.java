package kingdomino.swing.viewmodels;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;

public final class DominoViewModel {

	private final ImageIcon terrain1;
	private final ImageIcon terrain2;
	private final int crowsCount1;
	private final int crowsCount2;
	private final Color color;

	public DominoViewModel(ImageIcon terrain1, int crownsCount1, ImageIcon terrain2, int crownsCount2,
			Color color) {
		this.terrain1 = terrain1;
		this.terrain2 = terrain2;
		this.crowsCount1=crownsCount1;
		this.crowsCount2=crownsCount2;
		this.color = color;
	}


	public Image getImage(int i) {
		return i==0 ? terrain1.getImage() : terrain2.getImage();
	}

	public Color getColor() {
		return this.color;
	}


	public int getCrowsCount(int i) {
		return i==0 ? crowsCount1 : crowsCount2;
	}


	public boolean hasColor() {
		return !Color.WHITE.equals(color);
	}
}
