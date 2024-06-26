package kingdomino.domains;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class KingdomTest {
	@Test
	public void initsWithCapacity() {
		var kingdom = Kingdom.ofCapacity(5);
		
		assertEquals(5, kingdom.getCapacity());
		assertEquals(Tile.castle, kingdom.getTileAt(Coordinate.ofRowCol(4, 4)));
	}
	
	@Test
	public void replacesNegativeCapacityWith5() {
		var kingdom = Kingdom.ofCapacity(-5);

		assertEquals(5, kingdom.getCapacity());
		assertEquals(Tile.castle, kingdom.getTileAt(Coordinate.ofRowCol(4, 4)));
	}
	
	@Test
	public void replacesPairCapacityWith5() {
		var kingdom = Kingdom.ofCapacity(4);

		assertEquals(5, kingdom.getCapacity());
		assertEquals(Tile.castle, kingdom.getTileAt(Coordinate.ofRowCol(4, 4)));
	}
	
	@Test
	public void providesTileDataWithGetter() {
		var kingdom = Kingdom.ofCapacity(5);
		
		assertEquals(Terrain.CASTLE, kingdom.getTerrainAt(Coordinate.ofRowCol(4, 4)));
		assertEquals(0, kingdom.getCrownsCountAt(Coordinate.ofRowCol(4, 4)));
	}
	
	@Test
	public void returnsEmptyTileWhenCoordinateDoesNotMatch() {
		var kingdom = Kingdom.ofCapacity(5);
		
		assertEquals(5, kingdom.getCapacity());
		assertEquals(Tile.empty, kingdom.getTileAt(Coordinate.ofRowCol(0, 0)));
		assertEquals(Tile.empty, kingdom.getTileAt(null));
	}
	
	@Test
	public void acceptsConnectOnFirstTile() {
		var kingdom = Kingdom.ofCapacity(5);
		var domino = new Domino(1, new Tile(Terrain.FIELD,0), new Tile(Terrain.SWAMP,0));
		
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(4, 3), Coordinate.ofRowCol(3, 3));
		
		assertEquals(Terrain.FIELD, kingdom.getTerrainAt(Coordinate.ofRowCol(4, 3)));
		assertEquals(Terrain.SWAMP, kingdom.getTerrainAt(Coordinate.ofRowCol(3, 3)));
	}
	
	@Test
	public void acceptsConnectOnSecondTile() {
		var kingdom = Kingdom.ofCapacity(5);
		var domino = new Domino(1, new Tile(Terrain.FIELD,0), new Tile(Terrain.SWAMP,0));
		
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(3, 5), Coordinate.ofRowCol(4, 5));
		
		assertEquals(Terrain.FIELD, kingdom.getTerrainAt(Coordinate.ofRowCol(3, 5)));
		assertEquals(Terrain.SWAMP, kingdom.getTerrainAt(Coordinate.ofRowCol(4, 5)));
	}
	
	@Test
	public void rejectsSetsOnUnconnectedDomino() {
		var kingdom = Kingdom.ofCapacity(5);
		var domino = new Domino(1, new Tile(Terrain.FIELD,0), new Tile(Terrain.SWAMP,0));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(4, 3), Coordinate.ofRowCol(3, 3));
		//Too far
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(0, 0), Coordinate.ofRowCol(0, 1));
		
		assertEquals(Terrain.EMPTY, kingdom.getTerrainAt(Coordinate.ofRowCol(0, 0)));
		assertEquals(Terrain.EMPTY, kingdom.getTerrainAt(Coordinate.ofRowCol(0, 1)));
	}
	
	@Test
	public void rejectsSetOnFirstTileOverride() {
		var kingdom = Kingdom.ofCapacity(5);
		var domino = new Domino(1, new Tile(Terrain.FIELD,0), new Tile(Terrain.SWAMP,0));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(4, 3), Coordinate.ofRowCol(3, 3));
		
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(4, 3), Coordinate.ofRowCol(2, 3));
		
		assertEquals(Terrain.EMPTY, kingdom.getTerrainAt(Coordinate.ofRowCol(2, 3)));
	}
	
	@Test
	public void rejectsSetOnSecondTileOverride() {
		var kingdom = Kingdom.ofCapacity(5);
		var domino = new Domino(1, new Tile(Terrain.FIELD,0), new Tile(Terrain.SWAMP,0));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(4, 3), Coordinate.ofRowCol(3, 3));
		
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(2, 3), Coordinate.ofRowCol(3, 3));
		
		assertEquals(Terrain.EMPTY, kingdom.getTerrainAt(Coordinate.ofRowCol(2, 3)));
	}
	
	@Test
	public void rejectsNull() {
		var kingdom = Kingdom.ofCapacity(5);
		var domino = new Domino(1, new Tile(Terrain.FIELD,0), new Tile(Terrain.SWAMP,0));
		
		kingdom.setDominoAt(null, Coordinate.ofRowCol(4, 3), Coordinate.ofRowCol(3, 3));
		kingdom.setDominoAt(domino, null, Coordinate.ofRowCol(3, 3));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(4, 3), null);
		
		assertEquals(Terrain.EMPTY, kingdom.getTerrainAt(Coordinate.ofRowCol(2, 3)));
		assertEquals(Terrain.EMPTY, kingdom.getTerrainAt(Coordinate.ofRowCol(4, 3)));
	}
	
	@Test
	public void rejectsOutOfHeightMove() {
		var kingdom = Kingdom.ofCapacity(3);
		var domino = new Domino(1, new Tile(Terrain.FIELD,0), new Tile(Terrain.SWAMP,0));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(3, 4), Coordinate.ofRowCol(2, 4));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(1, 3), Coordinate.ofRowCol(1, 4));
	
		assertEquals(Terrain.EMPTY, kingdom.getTerrainAt(Coordinate.ofRowCol(1, 3)));
		assertEquals(Terrain.EMPTY, kingdom.getTerrainAt(Coordinate.ofRowCol(1, 4)));
	}
	
	@Test
	public void rejectsOutOfWidthMove() {
		var kingdom = Kingdom.ofCapacity(3);
		var domino = new Domino(1, new Tile(Terrain.FIELD,0), new Tile(Terrain.SWAMP,0));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(4, 5), Coordinate.ofRowCol(4, 6));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(4, 3), Coordinate.ofRowCol(5, 3));
	
		assertEquals(Terrain.EMPTY, kingdom.getTerrainAt(Coordinate.ofRowCol(4, 3)));
		assertEquals(Terrain.EMPTY, kingdom.getTerrainAt(Coordinate.ofRowCol(5, 3)));
	}
	
	@Test
	public void isCenterKingdomFullTilesFastGame() {
		var kingdom = Kingdom.ofCapacity(3);
		var domino = new Domino(1, new Tile(Terrain.FIELD,0), new Tile(Terrain.SWAMP,0));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(3, 3), Coordinate.ofRowCol(3, 4));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(4, 3), Coordinate.ofRowCol(5, 3));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(5, 4), Coordinate.ofRowCol(5, 5));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(3, 5), Coordinate.ofRowCol(4, 5));
		
		assertTrue(kingdom.kingdomIsCenter());
		
		
	}
	
	@Test
	public void isNotCenterKingdom() {
		var kingdom = Kingdom.ofCapacity(3);
		var domino = new Domino(1, new Tile(Terrain.FIELD,0), new Tile(Terrain.SWAMP,0));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(3, 3), Coordinate.ofRowCol(3, 4));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(4, 3), Coordinate.ofRowCol(5, 3));

		
		assertFalse(kingdom.kingdomIsCenter());
		
		
	}
	
	@Test
	public void isCenterTwoTilesFastGameOneTopLeftHorizontalAndOneBottomRightHorizontal() {
		var kingdom = Kingdom.ofCapacity(3);
		var domino = new Domino(1, new Tile(Terrain.FIELD,0), new Tile(Terrain.SWAMP,0));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(3, 3), Coordinate.ofRowCol(4, 3));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(4, 5), Coordinate.ofRowCol(5, 5));

		
		assertTrue(kingdom.kingdomIsCenter());
		
	}
	
	@Test
	public void isCenterTwoTilesFastGameOneTopRightHorizontalAndOneBottomLeftHorizontal() {
		var kingdom = Kingdom.ofCapacity(3);
		var domino = new Domino(1, new Tile(Terrain.FIELD,0), new Tile(Terrain.SWAMP,0));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(4, 3), Coordinate.ofRowCol(5, 3));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(3, 5), Coordinate.ofRowCol(4, 5));

		
		assertTrue(kingdom.kingdomIsCenter());
		
	}
	
	@Test
	public void isCenterTwoTilesFastGameOneTopLeftVerticalAndOneBottomRightVertical() {
		var kingdom = Kingdom.ofCapacity(3);
		var domino = new Domino(1, new Tile(Terrain.FIELD,0), new Tile(Terrain.SWAMP,0));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(4, 3), Coordinate.ofRowCol(5, 3));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(3, 5), Coordinate.ofRowCol(4, 5));

		
		assertTrue(kingdom.kingdomIsCenter());
		
	}
	
	@Test
	public void isCenterTwoTilesFastGameOneTopRightVerticalAndOneBottomLeftVertical() {
		var kingdom = Kingdom.ofCapacity(3);
		var domino = new Domino(1, new Tile(Terrain.FIELD,0), new Tile(Terrain.SWAMP,0));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(5, 3), Coordinate.ofRowCol(5, 4));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(3, 4), Coordinate.ofRowCol(3, 5));

		
		assertTrue(kingdom.kingdomIsCenter());
		
	}
	
	@Test
	public void isCenterNofastWithNoFullTiles() {
		var kingdom = Kingdom.ofCapacity(5);
		var domino = new Domino(1, new Tile(Terrain.FIELD,0), new Tile(Terrain.SWAMP,0));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(3, 3), Coordinate.ofRowCol(4, 3));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(2, 2), Coordinate.ofRowCol(3, 2));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(4, 5), Coordinate.ofRowCol(5, 5));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(5, 6), Coordinate.ofRowCol(6, 6));
		assertTrue(kingdom.kingdomIsCenter());
		
	}
	
	@Test
	public void isCenterNofastWithFullTiles() {
		var kingdom = Kingdom.ofCapacity(5);
		var domino = new Domino(1, new Tile(Terrain.FIELD,0), new Tile(Terrain.SWAMP,0));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(3, 3), Coordinate.ofRowCol(3, 4));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(3, 5), Coordinate.ofRowCol(4, 5));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(5, 5), Coordinate.ofRowCol(5, 4));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(5, 3), Coordinate.ofRowCol(4, 3));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(5, 3), Coordinate.ofRowCol(4, 3));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(2, 3), Coordinate.ofRowCol(2, 2));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(2, 4), Coordinate.ofRowCol(2, 5));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(2, 6), Coordinate.ofRowCol(3, 6));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(5, 6), Coordinate.ofRowCol(4, 6));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(6, 6), Coordinate.ofRowCol(6, 5));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(6, 3), Coordinate.ofRowCol(6, 4));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(6, 2), Coordinate.ofRowCol(5, 2));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(3, 2), Coordinate.ofRowCol(4, 2));
		assertTrue(kingdom.kingdomIsCenter());
		
	}
	
	@Test
	public void isNotCenterNofastWithFullTiles() {
		var kingdom = Kingdom.ofCapacity(5);
		var domino = new Domino(1, new Tile(Terrain.FIELD,0), new Tile(Terrain.SWAMP,0));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(3, 3), Coordinate.ofRowCol(3, 4));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(3, 5), Coordinate.ofRowCol(4, 5));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(3, 5), Coordinate.ofRowCol(4, 5));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(5, 5), Coordinate.ofRowCol(5, 4));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(5, 6), Coordinate.ofRowCol(4, 6));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(6, 6), Coordinate.ofRowCol(6, 5));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(6, 3), Coordinate.ofRowCol(6, 4));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(6, 2), Coordinate.ofRowCol(5, 2));
		kingdom.setDominoAt(domino, Coordinate.ofRowCol(3, 2), Coordinate.ofRowCol(4, 2));
		assertFalse(kingdom.kingdomIsCenter());
		
	}
	
	@Test
	public void isCenterKingdomAlone() {
		var kingdom = Kingdom.ofCapacity(3);
		assertTrue(kingdom.kingdomIsCenter());
	}

	
	@Test
	public void playableTilesKingdomAloneCoord() {
		var kingdom = Kingdom.ofCapacity(3);
		var domino = new Domino(1, new Tile(Terrain.FIELD,0), new Tile(Terrain.SWAMP,0));
		Set<Coordinate> playableTiles = kingdom.playableTiles(domino);
		Set<Coordinate> playableTilesComparaison = new HashSet<>();
		playableTilesComparaison.add(Coordinate.ofRowCol(3, 4));
		playableTilesComparaison.add(Coordinate.ofRowCol(4, 3));
		playableTilesComparaison.add(Coordinate.ofRowCol(5, 4));
		playableTilesComparaison.add(Coordinate.ofRowCol(4, 5));
		assertEquals(playableTiles, playableTilesComparaison);
		
	}
	
	@Test
	public void playableTilesChâteauSitueCoinSansAvoirAtteintLesLimites() {
		var kingdom = Kingdom.ofCapacity(5);
		var domino1 = new Domino(1, new Tile(Terrain.LAKE,0), new Tile(Terrain.FOREST,0));
		var domino2 = new Domino(2, new Tile(Terrain.FOREST, 0), new Tile(Terrain.FOREST, 0));
		var currentDomino = new Domino(3, new Tile(Terrain.FIELD, 0), new Tile(Terrain.FOREST, 0));	

		kingdom.setDominoAt(domino1, Coordinate.ofRowCol(4, 2), Coordinate.ofRowCol(4, 3));
		kingdom.setDominoAt(domino2, Coordinate.ofRowCol(3, 4), Coordinate.ofRowCol(2, 4));
		
		Set<Coordinate> playableTiles = kingdom.playableTiles(currentDomino);
		Set<Coordinate> playableTilesComparaison = new HashSet<>();
		playableTilesComparaison.add(Coordinate.ofRowCol(3, 3));
		playableTilesComparaison.add(Coordinate.ofRowCol(2, 3));
		playableTilesComparaison.add(Coordinate.ofRowCol(1, 4));
		playableTilesComparaison.add(Coordinate.ofRowCol(2, 5));
		playableTilesComparaison.add(Coordinate.ofRowCol(3, 5));
		playableTilesComparaison.add(Coordinate.ofRowCol(4, 5));
		playableTilesComparaison.add(Coordinate.ofRowCol(5, 4));
		playableTilesComparaison.add(Coordinate.ofRowCol(5, 3));
		
		assertEquals(playableTiles, playableTilesComparaison);
		
	}
	
	@Test
	public void playableTilesChâteauSitueCoinEnAyantAtteintLesLimites() {
		var kingdom = Kingdom.ofCapacity(5);
		var domino1 = new Domino(1, new Tile(Terrain.FIELD,0), new Tile(Terrain.FOREST,0));
		var domino2 = new Domino(2, new Tile(Terrain.MINE, 0), new Tile(Terrain.FIELD, 0));
		var domino3 = new Domino(3, new Tile(Terrain.SWAMP, 0), new Tile(Terrain.SWAMP, 0));
		var domino4 = new Domino(4, new Tile(Terrain.SWAMP, 0), new Tile(Terrain.FIELD,0));
		var currentDomino = new Domino(5, new Tile(Terrain.GRASSLANDS, 0), new Tile(Terrain.FIELD, 0));	

		kingdom.setDominoAt(domino1, Coordinate.ofRowCol(4, 2), Coordinate.ofRowCol(4, 3));
		kingdom.setDominoAt(domino2, Coordinate.ofRowCol(4, 0), Coordinate.ofRowCol(4, 1));
		kingdom.setDominoAt(domino3, Coordinate.ofRowCol(3, 4), Coordinate.ofRowCol(2, 4));
		kingdom.setDominoAt(domino4, Coordinate.ofRowCol(1, 4), Coordinate.ofRowCol(0, 4));
	
		Set<Coordinate> playableTiles = kingdom.playableTiles(currentDomino);
		Set<Coordinate> playableTilesComparaison = new HashSet<>();
		playableTilesComparaison.add(Coordinate.ofRowCol(0, 3));
		playableTilesComparaison.add(Coordinate.ofRowCol(3, 1));
		playableTilesComparaison.add(Coordinate.ofRowCol(3, 2));
		

		
		assertEquals(playableTiles, playableTilesComparaison);
		
	}
	
	@Test
	public void playableTilesChâteauFormeRectangulaire() {
		var kingdom = Kingdom.ofCapacity(5);
		var domino1 = new Domino(1, new Tile(Terrain.FOREST,0), new Tile(Terrain.FIELD,0));
		var domino2 = new Domino(2, new Tile(Terrain.LAKE, 0), new Tile(Terrain.FOREST, 0));
		var domino4 = new Domino(4, new Tile(Terrain.SWAMP, 0), new Tile(Terrain.FIELD,0));
		var domino5 = new Domino(5, new Tile(Terrain.LAKE, 0), new Tile(Terrain.FOREST,0));
		var domino6 = new Domino(6, new Tile(Terrain.FOREST, 0), new Tile(Terrain.FIELD,0));
		var domino7 = new Domino(7, new Tile(Terrain.FIELD, 0), new Tile(Terrain.LAKE,0));
		var domino8 = new Domino(8, new Tile(Terrain.FIELD, 0), new Tile(Terrain.GRASSLANDS,0));
		var currentDomino = new Domino(9, new Tile(Terrain.FOREST, 0), new Tile(Terrain.SWAMP, 0));	

		kingdom.setDominoAt(domino1, Coordinate.ofRowCol(3, 3), Coordinate.ofRowCol(3, 4));
		kingdom.setDominoAt(domino2, Coordinate.ofRowCol(3, 1), Coordinate.ofRowCol(3, 2));
		kingdom.setDominoAt(domino4, Coordinate.ofRowCol(4, 2), Coordinate.ofRowCol(4, 3));
		kingdom.setDominoAt(domino5, Coordinate.ofRowCol(4, 1), Coordinate.ofRowCol(5, 1));
		kingdom.setDominoAt(domino6, Coordinate.ofRowCol(5, 2), Coordinate.ofRowCol(5, 3));
		kingdom.setDominoAt(domino7, Coordinate.ofRowCol(5, 4), Coordinate.ofRowCol(5, 5));
		kingdom.setDominoAt(domino8, Coordinate.ofRowCol(4, 5), Coordinate.ofRowCol(3, 5));
	
		Set<Coordinate> playableTiles = kingdom.playableTiles(currentDomino);
		Set<Coordinate> playableTilesComparaison = new HashSet<>();
		playableTilesComparaison.add(Coordinate.ofRowCol(6, 1));
		playableTilesComparaison.add(Coordinate.ofRowCol(6, 2));
		playableTilesComparaison.add(Coordinate.ofRowCol(2, 2));
		playableTilesComparaison.add(Coordinate.ofRowCol(2, 3));
		

		
		assertEquals(playableTiles, playableTilesComparaison);
		
	}
	
	@Test
	public void playableTilesChâteauFormeSymétrique() {
		var kingdom = Kingdom.ofCapacity(5);
		var domino1 = new Domino(1, new Tile(Terrain.FIELD,0), new Tile(Terrain.FIELD,0));
		var domino2 = new Domino(2, new Tile(Terrain.FIELD, 0), new Tile(Terrain.SWAMP, 0));
		var currentDomino = new Domino(9, new Tile(Terrain.FIELD, 0), new Tile(Terrain.LAKE, 0));	

		kingdom.setDominoAt(domino1, Coordinate.ofRowCol(4, 3), Coordinate.ofRowCol(4, 2));
		kingdom.setDominoAt(domino2, Coordinate.ofRowCol(4, 5), Coordinate.ofRowCol(4, 6));
	
		Set<Coordinate> playableTiles = kingdom.playableTiles(currentDomino);
		Set<Coordinate> playableTilesComparaison = new HashSet<>();
		playableTilesComparaison.add(Coordinate.ofRowCol(3, 2));
		playableTilesComparaison.add(Coordinate.ofRowCol(3, 3));
		playableTilesComparaison.add(Coordinate.ofRowCol(3, 4));
		playableTilesComparaison.add(Coordinate.ofRowCol(3, 5));
		playableTilesComparaison.add(Coordinate.ofRowCol(5, 2));
		playableTilesComparaison.add(Coordinate.ofRowCol(5, 3));
		playableTilesComparaison.add(Coordinate.ofRowCol(5, 4));
		playableTilesComparaison.add(Coordinate.ofRowCol(5, 5));

		

		
		assertEquals(playableTiles, playableTilesComparaison);
		
	}
	

	
	
	
}
