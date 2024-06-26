package kingdomino.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DominoTest {
	@Test
	public void initsWithNumberAndTwoTiles() {
		Domino d = new Domino(3, aForest(2), aLake(1));
		
		assertEquals(aForest(2), d.getTile(0));
		assertEquals(2, d.getCrownsCount(0));
		assertEquals(Terrain.FOREST, d.getTerrain(0));
		
		assertEquals(aLake(1), d.getTile(1));
		assertEquals(1, d.getCrownsCount(1));
		assertEquals(Terrain.LAKE, d.getTerrain(1));
	}
	
	@Test
	public void initsWithEmptyTilesOnNullTilesArgs() {
		Domino d = new Domino(3, null, null);
		

		assertEquals(0, d.getCrownsCount(0));
		assertEquals(Terrain.EMPTY, d.getTerrain(0));
		
		assertEquals(0, d.getCrownsCount(1));
		assertEquals(Terrain.EMPTY, d.getTerrain(1));
	}
	
	@Test
	public void returnsEmptyTerrainOnWrongTilePos() {
		Domino d = new Domino(3, aForest(2), aLake(1));
		
		assertEquals(Tile.empty, d.getTile(2));
		assertEquals(0, d.getCrownsCount(2));
		assertEquals(Terrain.EMPTY, d.getTerrain(2));
	}
	
	@Test
	public void isEquatable() {
		Domino domino = new Domino(3, aForest(2), aLake(1));
		Domino clone = new Domino(3, aForest(2), aLake(1));
		
		assertEquals(domino, domino);
		assertEquals(domino, clone);
		assertEquals(clone, domino);
		
		assertNotEquals(domino, null);
		assertNotEquals(domino, new Domino(2, aForest(2), aLake(1)));
	}
	
	@Test
	public void respectsTheHashCodeContract() {
		Domino domino = new Domino(3, aForest(2), aLake(1));
		Domino clone = new Domino(3, aForest(2), aLake(1));
		
		assertEquals(domino.hashCode(), clone.hashCode());
	}
	
	@Test
	public void isComparableOnTheirId() {
		Domino domino = new Domino(3, aForest(2), aLake(1));
		Domino clone = new Domino(3, aForest(2), aLake(1));
		Domino before = new Domino(2, aForest(2), aLake(1));
		Domino after = new Domino(4, aForest(2), aLake(1));
		
		assertEquals(0, domino.compareTo(clone));
		
		assertEquals(-1, before.compareTo(domino));
		assertEquals(1, domino.compareTo(before));
		
		assertEquals(-2, before.compareTo(after));
		assertEquals(2, after.compareTo(before));
		
		assertEquals(Integer.MIN_VALUE, domino.compareTo(null));
	}
	
	private Tile aLake(int cc) {
		return new Tile(Terrain.LAKE, cc);
	}

	private Tile aForest(int cc) {
		return new Tile(Terrain.FOREST, cc);
	}
}
