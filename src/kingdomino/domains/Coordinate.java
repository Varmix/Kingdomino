package kingdomino.domains;

import java.util.*;

/**
 * Décrit une position à l'aide de numéros de ligne et de colonne.
 * Effectue des opérations élémentaires sur les coordonnées comme des translations et des rotations de 90°.
 * 
 * Les objets de cette classe sont immuables.
 * */
public class Coordinate {
	public static final Coordinate ZERO = new Coordinate(0, 0);
	private final int row;
	private final int col;

	/**
	 * Retourne une nouvelle coordonnée {@code (row, col)}.
	 * */
	public static Coordinate ofRowCol(int row, int col) {
		return new Coordinate(row,col);
	}
	
	private Coordinate(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	/**
	 * Retourne les voisins de cette coordonnées sous forme d'une collection immuable.
	 * Attention, cette méthode ne tient pas compte d'éventuels débordements.
	 * */
	public Collection<Coordinate> getNeighbors() {
		return List.of(
			this.translate(-1, 0),
			this.translate(0, 1),
			this.translate(1, 0),
			this.translate(0, -1)
		);
	}
	
	/**
	 * Retourne la coordonnée {@code (this.row + dr, this.col + dc)}.
	 * */
	public Coordinate translate(int dr, int dc) {
		return new Coordinate(row + dr, col + dc);
	}

	/**
	 * Retourne la coordonnée résultant de la rotation de cette coordonnée par rapport à {@code origin}.
	 * 
	 * La rotation se fait dans le sens inverse des aiguilles d'une montre.
	 * */
	public Coordinate rotate90From(Coordinate origin) {
		int dy = origin.row - row;
		int dx = origin.col - col; 
		
		return new Coordinate(origin.row + dx, origin.col - dy);
	}

	@Override
	public boolean equals(Object other) {
		if(this == other) {
			return true;
		}
		
		if(!(other instanceof Coordinate)) {
			return false;
		}
		
		var that = (Coordinate)other;
		return this.row == that.row && this.col == that.col;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(row, col);
	}

	@Override
	public String toString() {
		return String.format("Coord(row: %d, col: %d)", row, col);
	}



}
