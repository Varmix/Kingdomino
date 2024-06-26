package kingdomino.domains.score.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import kingdomino.domains.Coordinate;
import kingdomino.domains.Terrain;
import kingdomino.domains.Tile;

public class TerritoryTest {
	@Test
	public void computesScoreForSampleOne() {

		var territory = Territory.ofCoordinateAndTerrain(Coordinate.ofRowCol(0, 0), new Tile(Terrain.MINE, 1));
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				territory.add(Coordinate.ofRowCol(i, j), new Tile(Terrain.MINE, 1));

			}
		}
		assertEquals(9 * 9, territory.getScore());
	}

	@Test
	public void ignoresTileOfOtherTerrainType() {
		var territory = Territory.ofCoordinateAndTerrain(Coordinate.ofRowCol(0, 0), new Tile(Terrain.LAKE, 2));

		territory.add(Coordinate.ofRowCol(0, 1), new Tile(Terrain.MINE, 3));
		territory.add(Coordinate.ofRowCol(0, 2), new Tile(Terrain.MINE, 3));
		territory.add(Coordinate.ofRowCol(1, 0), new Tile(Terrain.MINE, 1));
		territory.add(Coordinate.ofRowCol(1, 2), new Tile(Terrain.MINE, 3));
		territory.add(Coordinate.ofRowCol(2, 0), new Tile(Terrain.MINE, 1));
		territory.add(Coordinate.ofRowCol(2, 1), new Tile(Terrain.MINE, 1));
		
		assertEquals(2, territory.getScore());
	}
	
	@Test
	public void ignoresNotConnectedTile() {
		var territory = Territory.ofCoordinateAndTerrain(Coordinate.ofRowCol(0, 0), new Tile(Terrain.LAKE, 2));

		territory.add(Coordinate.ofRowCol(1, 1), new Tile(Terrain.LAKE, 2));
		territory.add(Coordinate.ofRowCol(2, 2), new Tile(Terrain.LAKE, 2));
		
		assertEquals(2, territory.getScore());
	}
	
	@Test
	public void ignoresNullCoordAndTile() {
		var territory = Territory.ofCoordinateAndTerrain(Coordinate.ofRowCol(0, 0), new Tile(Terrain.LAKE, 2));
		territory.add(Coordinate.ofRowCol(0, 1), new Tile(Terrain.LAKE, 2));
		territory.add(null, new Tile(Terrain.LAKE, 2));
		territory.add(Coordinate.ofRowCol(1, 0), null);
		
		assertEquals(2*4, territory.getScore());
	}
}
