package kingdomino.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TileTest {
	@Test
	public void initsWithValidTerrainAndCrownsCount() {
		var tile = new Tile(Terrain.CASTLE, 0);
		
		assertEquals(Terrain.CASTLE, tile.getTerrain());
		assertEquals(0, tile.getCrownsCount());
	}
	
	@Test
	public void replacesNegativeCrownsCountByZeroOnInits() {
		var tile = new Tile(Terrain.CASTLE, -1);
		
		assertEquals(0, tile.getCrownsCount());
	}
	
	@Test
	public void replacesTooMuchCrownsCountBy3() {
		var tile = new Tile(Terrain.CASTLE, 4);
		
		assertEquals(3, tile.getCrownsCount());
		
	}
	
	@Test
	public void replacesNullTerrainByEmpty() {
		var tile = new Tile(null, 2);
		
		assertEquals(Terrain.EMPTY, tile.getTerrain());
	}
	
	@Test
	public void tellsIfItIsCompatibleWithAnother() {
		var castleTile = new Tile(Terrain.CASTLE, 0);
		var fieldTile =  new Tile(Terrain.FIELD, 1);

		//are compatible with same terrain tile
		assertTrue(fieldTile.isTerrainCompatibleWith(Terrain.FIELD));
		
		//are compatible with castle
		assertTrue(fieldTile.isTerrainCompatibleWith(Terrain.CASTLE));
		assertTrue(castleTile.isTerrainCompatibleWith(Terrain.SWAMP));
		assertTrue(castleTile.isTerrainCompatibleWith(Terrain.FIELD));
		
		//are not compatible with other terrain tile
		assertFalse(fieldTile.isTerrainCompatibleWith(Terrain.SWAMP));
	}
	
	@Test
	public void isEquatable() {
		var fieldTile =  new Tile(Terrain.FIELD, 1);
		var clone = new Tile(Terrain.FIELD, 1);
		
		assertEquals(fieldTile, fieldTile);
		assertEquals(fieldTile, clone);
		assertEquals(clone, fieldTile);
	}
	
	@Test
	public void differsOnTerrainAndCrownsCount() {
		var fieldTile =  new Tile(Terrain.FIELD, 1);
		var otherCC = new Tile(Terrain.FIELD, 2);
		var otherTerrain = new Tile(Terrain.MINE, 1);
		
		assertNotEquals(fieldTile, otherCC);
		assertNotEquals(otherCC, fieldTile);
		
		assertNotEquals(fieldTile, otherTerrain);
		assertNotEquals(otherTerrain, fieldTile);
		
		assertFalse(fieldTile.equals(null));
	}
	
	@Test
	public void knowsIfItIsStandard() {
		var fieldTile =  new Tile(Terrain.FIELD, 1);
		
		assertTrue(fieldTile.isTerrainStandard());
		assertFalse(Tile.castle.isTerrainStandard());
		assertFalse(Tile.empty.isTerrainStandard());
	}
}
