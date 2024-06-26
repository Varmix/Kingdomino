package kingdomino.domains;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PlayerTest {
	private Player player;
	private Kingdom kingdom;
	
	@Test
	public void initsFromNameAndHexColor() {
		var player = new Player("HenNi","#ff00ff");
		
		assertEquals("HenNi", player.getName());
		assertEquals("#ff00ff", player.getHexColor());
		assertEquals(Kingdom.EMPTY, player.getKingdom());
		assertEquals(0, player.getScore());
	}
	
	@Test
	public void handlesHisKingdom() {
		givenAPlayerWithAFiveCapacityKingdom();
		
		assertSame(kingdom, player.getKingdom());
	}
	
	@Test
	public void asksTheKingdomIfAMoveIsValid() {
		givenAPlayerWithAFiveCapacityKingdom();
		
		assertFalse(player.setDominoAt(
				new Domino(2, new Tile(Terrain.FIELD, 0), new Tile(Terrain.MINE, 2)), 
				Coordinate.ofRowCol(4, 2), 
				Coordinate.ofRowCol(5, 2)));
		
		assertTrue(player.setDominoAt(
				new Domino(2, new Tile(Terrain.FIELD, 0), new Tile(Terrain.MINE, 2)), 
				Coordinate.ofRowCol(4, 3), 
				Coordinate.ofRowCol(5, 3)));
	}
	
	@Test
	public void updatesKingdom() {
		givenAPlayerWithAFiveCapacityKingdom();
		andAScoreOfZero();
		
		player.setDominoAt(
				new Domino(2, new Tile(Terrain.FIELD, 0), new Tile(Terrain.MINE, 2)), 
				Coordinate.ofRowCol(4, 3), 
				Coordinate.ofRowCol(5, 3));
		
		assertNotEquals(0, player.getScore());
	}

	private void andAScoreOfZero() {
		assertEquals(0, player.getScore());
	}

	private void givenAPlayerWithAFiveCapacityKingdom() {
		player = new Player("HenNi","#ff00ff");
		kingdom = Kingdom.ofCapacity(5);
		
		player.setKingdom(kingdom);
	}
	
	@Test
	public void comparesToOtherPlayerBasedOnScore() {
		givenAPlayerWithAFiveCapacityKingdom();
		Player opponent = andAnotherPlayerWithAScoreGreaterThanZero();
		
		int comparison1 = player.compareTo(opponent);
		int comparison2 = opponent.compareTo(player);
		
		assertNotEquals(comparison1, comparison2);
		assertEquals(Math.abs(comparison1), Math.abs(comparison2));
	}

	private Player andAnotherPlayerWithAScoreGreaterThanZero() {
		var player = new Player("HenNi","#ff00ff");
		var kingdom = Kingdom.ofCapacity(5);
		
		player.setKingdom(kingdom);
		player.setDominoAt(
				new Domino(2, new Tile(Terrain.FIELD, 0), new Tile(Terrain.MINE, 2)), 
				Coordinate.ofRowCol(4, 3), 
				Coordinate.ofRowCol(5, 3));
		
		return player;
	}
	
}
