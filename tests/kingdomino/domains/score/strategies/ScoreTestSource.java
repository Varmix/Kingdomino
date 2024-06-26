package kingdomino.domains.score.strategies;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import kingdomino.domains.Coordinate;
import kingdomino.domains.Domino;
import kingdomino.domains.Kingdom;
import kingdomino.domains.Terrain;
import kingdomino.domains.Tile;

/**
 * Describes various kingdoms and expected values
 * */
public class ScoreTestSource {
	private final static Kingdom ALL_MINES_KINGDOM = Kingdom.ofCapacity(5);
	static {
		var mine = new Domino(1, new Tile(Terrain.MINE, 1), new Tile(Terrain.MINE, 1));

		ALL_MINES_KINGDOM.setDominoAt(mine, Coordinate.ofRowCol(4, 3), Coordinate.ofRowCol(3, 3));
		ALL_MINES_KINGDOM.setDominoAt(mine, Coordinate.ofRowCol(3, 4), Coordinate.ofRowCol(3, 5));
		ALL_MINES_KINGDOM.setDominoAt(mine, Coordinate.ofRowCol(4, 5), Coordinate.ofRowCol(5, 5));
		ALL_MINES_KINGDOM.setDominoAt(mine, Coordinate.ofRowCol(5, 4), Coordinate.ofRowCol(5, 3));
	}
	
	private final static Kingdom TWO_TERRAIN_KINGDOM = Kingdom.ofCapacity(5);
	static {
		var lake = new Domino(1, new Tile(Terrain.LAKE, 1), new Tile(Terrain.LAKE, 1));
		var field = new Domino(2, new Tile(Terrain.FIELD, 2), new Tile(Terrain.FIELD, 2));
		
		TWO_TERRAIN_KINGDOM.setDominoAt(lake, Coordinate.ofRowCol(4, 3), Coordinate.ofRowCol(3, 3));
		TWO_TERRAIN_KINGDOM.setDominoAt(lake, Coordinate.ofRowCol(3, 4), Coordinate.ofRowCol(3, 5));
		TWO_TERRAIN_KINGDOM.setDominoAt(field, Coordinate.ofRowCol(4, 5), Coordinate.ofRowCol(5, 5));
		TWO_TERRAIN_KINGDOM.setDominoAt(field, Coordinate.ofRowCol(5, 4), Coordinate.ofRowCol(5, 3));
	}
	
	private final static Kingdom THREE_TERRAINS_KINGDOM = Kingdom.ofCapacity(5);
	static {
		var lake = new Domino(1, new Tile(Terrain.LAKE, 1), new Tile(Terrain.LAKE, 1));
		var field = new Domino(2, new Tile(Terrain.FIELD, 2), new Tile(Terrain.FIELD, 2));
		var forest = new Domino(3, new Tile(Terrain.FOREST, 3), new Tile(Terrain.FOREST, 3));
		
		THREE_TERRAINS_KINGDOM.setDominoAt(lake, Coordinate.ofRowCol(4, 3), Coordinate.ofRowCol(3, 3));
		THREE_TERRAINS_KINGDOM.setDominoAt(lake, Coordinate.ofRowCol(3, 4), Coordinate.ofRowCol(3, 5));
		THREE_TERRAINS_KINGDOM.setDominoAt(field, Coordinate.ofRowCol(5, 4), Coordinate.ofRowCol(5, 3));
		THREE_TERRAINS_KINGDOM.setDominoAt(forest, Coordinate.ofRowCol(4, 5), Coordinate.ofRowCol(4, 6));
	}
	
	private final static Kingdom ZERO_CROWN_KINGDOM = Kingdom.ofCapacity(5);
	static {
		var lake = new Domino(1, new Tile(Terrain.LAKE, 0), new Tile(Terrain.LAKE, 0));
		var field = new Domino(2, new Tile(Terrain.FIELD, 0), new Tile(Terrain.FIELD, 0));
		var forest = new Domino(3, new Tile(Terrain.FOREST, 0), new Tile(Terrain.FOREST, 0));
		
		ZERO_CROWN_KINGDOM.setDominoAt(lake, Coordinate.ofRowCol(4, 3), Coordinate.ofRowCol(3, 3));
		ZERO_CROWN_KINGDOM.setDominoAt(lake, Coordinate.ofRowCol(3, 4), Coordinate.ofRowCol(3, 5));
		ZERO_CROWN_KINGDOM.setDominoAt(field, Coordinate.ofRowCol(5, 4), Coordinate.ofRowCol(5, 3));
		ZERO_CROWN_KINGDOM.setDominoAt(forest, Coordinate.ofRowCol(4, 5), Coordinate.ofRowCol(4, 6));
	}
	
	private final static Kingdom TOP_LEFT_CASTLE_KINGOM = Kingdom.ofCapacity(5);
	static {
		var lake = new Domino(1, new Tile(Terrain.LAKE, 0), new Tile(Terrain.LAKE, 0));
		var field = new Domino(2, new Tile(Terrain.FIELD, 0), new Tile(Terrain.FIELD, 0));
		var lakeField = new Domino(3, new Tile(Terrain.LAKE, 1), new Tile(Terrain.FIELD, 1));
		
		TOP_LEFT_CASTLE_KINGOM.setDominoAt(lake, Coordinate.ofRowCol(4, 5), Coordinate.ofRowCol(4, 6));
		TOP_LEFT_CASTLE_KINGOM.setDominoAt(lake, Coordinate.ofRowCol(5, 5), Coordinate.ofRowCol(5, 6));
		TOP_LEFT_CASTLE_KINGOM.setDominoAt(field, Coordinate.ofRowCol(5, 4), Coordinate.ofRowCol(6, 4));
		TOP_LEFT_CASTLE_KINGOM.setDominoAt(field, Coordinate.ofRowCol(7, 4), Coordinate.ofRowCol(8, 4));
		TOP_LEFT_CASTLE_KINGOM.setDominoAt(lakeField, Coordinate.ofRowCol(6, 6), Coordinate.ofRowCol(6, 5));
	}
	
	public static Stream<Arguments> getSamples() {
		return Stream.of(
				Arguments.of(ALL_MINES_KINGDOM, 8*8),
				Arguments.of(TWO_TERRAIN_KINGDOM, 4*4 + 4*8),
				Arguments.of(THREE_TERRAINS_KINGDOM, 4*4 + 2*4 + 2*6),
				Arguments.of(ZERO_CROWN_KINGDOM, 0),
				Arguments.of(TOP_LEFT_CASTLE_KINGOM, 5*1 + 5*1)
		);
	}
}
