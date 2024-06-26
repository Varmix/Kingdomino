package kingdomino.domains;

import java.util.Objects;

/**
 * Un domino est un objet immuable composé de deux tuiles et d'un numéro l'identifiant.
 * Les dominos sont naturellement comparables selon leur identifiant.
 * 
 * Chaque tuile est associée à une position (0 pour la première et 1 pour la seconde) pour accéder à leur propriété.
 */
public final class Domino implements Comparable<Domino> {
	public static final Domino UNKNOWN = new Domino(0, Tile.empty, Tile.empty);
	private final int number;
	private final Tile[] tiles;

	/**
	 * Construit un nouveau domino d'identifiant {@code id} et composé des tiles {@code [part1; part2]}.
	 * 
	 * @param id un numéro a priori unique.
	 * @param part1 la première tuile. Si {@code part1 == null}, elle est remplacée par une tuile vide.
	 * @param part2 la seconde tuile. Si {@code part2 == null}, elle est remplacée par une tuile vide.
	 * */
	public Domino(int id, Tile part1, Tile part2) {
		this.number = id;
		this.tiles = new Tile[] { 
			part1 == null ? Tile.empty : part1, 
			part2 == null ? Tile.empty : part2
		};
	}

	/**
	 * @param tilePos la position de la tuile consultée. Vaut normalement 0 ou 1.
	 * @return le terrain de la tuile {@code tilePos} si la position est valable, Terrain.EMPTY sinon. 
	 * */
	public Terrain getTerrain(int tilePos) {
		return isValidTileIndex(tilePos) ? tiles[tilePos].getTerrain() : Terrain.EMPTY;
	}
	
	/**
	 * @param tilePos la position de la tuile consultée. Vaut normalement 0 ou 1.
	 * @return le nombre de couronnes de la tuile {@code tilePos} si la position est valable, 0 sinon. 
	 * */
	public int getCrownsCount(int tilePos) {
		return isValidTileIndex(tilePos) ? tiles[tilePos].getCrownsCount() : 0;
	}

	/**
	 * @param tilePos la position de la tuile consultée. Vaut normalement 0 ou 1.
	 * @return le nombre de couronnes de la tuile {@code tilePos} si la position est valable, 0 sinon. 
	 * */
	public Tile getTile(int tilePos) {
		return isValidTileIndex(tilePos) ? tiles[tilePos] : Tile.empty;
	}

	private boolean isValidTileIndex(int tilePos) {
		return tilePos == 0 || tilePos == 1;
	}

	@Override
	public int compareTo(Domino that) {
		return (that == null) ? Integer.MIN_VALUE : this.number - that.number;
	}

	@Override
	public String toString() {
		return String.format("Domino(number: %02d)", number);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}

		if (!(other instanceof Domino)) {
			return false;
		}
		var that = (Domino) other;

		return this.number == that.number;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.number);
	}
}
