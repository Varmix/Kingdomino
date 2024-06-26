package kingdomino.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import kingdomino.domains.dummies.*;

public class KingDominoGameTest {
	@Test
	public void initsWithDefaultsValues() {
		var players = DummyPlayerFactory.newPlayers("P1", "P2");
		var pile = DummyDominoPileFactory.newDominoPile(8, 8);
		
		var game = new KingDominoGame(players, pile);
		
		assertIterableEquals(players, game.getPlayers());
		assertEquals(DrawLine.EMPTY, game.getCurrentLine());
		assertEquals(Player.UNKNOWN, game.getCurrentPlayer());
		assertEquals(Domino.UNKNOWN, game.getCurrentDomino());
		
		assertEquals(DrawLine.EMPTY, game.getNextLine());
	}
	
	@Test
	public void startsByDrawingTwoLines() {
		var players = DummyPlayerFactory.newPlayers("P1", "P2");
		var pile = DummyDominoPileFactory.newDominoPile(8, 8);
		var game = new KingDominoGame(players, pile);
		
		game.start();
		
		assertNotEquals(DrawLine.EMPTY, game.getCurrentLine());
		assertTrue(players.contains(game.getCurrentPlayer()));
		assertNotEquals(Domino.UNKNOWN, game.getCurrentDomino());
		assertNotEquals(DrawLine.EMPTY, game.getNextLine());
	}
	
	@Test
	public void asksToPickTheNextDominoOnValidDominoSet() {
		var players = DummyPlayerFactory.newPlayers("P1", "P2");
		var pile = DummyDominoPileFactory.newDominoPile(8, 8);
		var game = new KingDominoGame(players, pile);
		game.start();
		
		var actualResult = game.setCurrentDominoAt(Coordinate.ofRowCol(4, 3), Coordinate.ofRowCol(4, 2));
		
		assertEquals(KingDominoActionResult.SELECT_NEXT_DOMINO, actualResult);
	}
	
	@Test
	public void asksToPickTheNextDominoOnSkip() {
		var players = DummyPlayerFactory.newPlayers("P1", "P2");
		var pile = DummyDominoPileFactory.newDominoPile(8, 8);
		var game = new KingDominoGame(players, pile);
		game.start();
		
		var actualResult = game.skip();
		
		assertEquals(KingDominoActionResult.SELECT_NEXT_DOMINO, actualResult);
	}
	
	@Test
	public void warnsOnInvalidDominoSet() {
		var players = DummyPlayerFactory.newPlayers("P1", "P2");
		var pile = DummyDominoPileFactory.newDominoPile(8, 8);
		var game = new KingDominoGame(players, pile);
		game.start();
		
		var actualResult = game.setCurrentDominoAt(Coordinate.ofRowCol(0,0), Coordinate.ofRowCol(0, 1));
		
		assertEquals(KingDominoActionResult.INVALID, actualResult);
	}
	
	@Test
	public void takeOneDominoOutOfCurrentLineOnNextDominoPicked() {
		var players = DummyPlayerFactory.newPlayers("P1", "P2");
		var pile = DummyDominoPileFactory.newDominoPile(8, 8);
		var game = new KingDominoGame(players, pile);
		game.start();
		
		game.setNextDomino();
		
		assertEquals(3, game.getCurrentLine().getCount());
	}
	
	@Test
	public void picksNextSelectableDominoInNextLine() {
		var players = DummyPlayerFactory.newPlayers("P1", "P2");
		var pile = DummyDominoPileFactory.newDominoPile(8, 8);
		var game = new KingDominoGame(players, pile);
		game.start();
		
		boolean found = false;
		for(var domino : game.getNextLine()) {
			found |= game.isNextDomino(domino);
		}
		assertTrue(found);
	}
	
	@Test
	public void switchesToNextLineOnAllCurrentDominosPlayed() {
		var players = DummyPlayerFactory.newPlayers("P1", "P2");
		var pile = DummyDominoPileFactory.newDominoPile(8, 8);
		var game = new KingDominoGame(players, pile);
		game.start();
		var oldNextLine = game.getNextLine();
		for(int i=0; i < 4; ++i) {
			game.skip();
			game.setNextDomino();
		}
		
		assertEquals(game.getCurrentLine(), oldNextLine);
		assertNotEquals(game.getNextLine(), oldNextLine);
	}
	
	@Test
	public void movesToNextPlayerOnNoNextLines() {
		var players = DummyPlayerFactory.newPlayers("P1", "P2");
		var pile = DummyDominoPileFactory.newDominoPile(8, 8);
		var game = new KingDominoGame(players, pile);
		game.start();
		
		for(int i=0; i < 4; ++i) {
			game.skip();
			game.setNextDomino();
		}
		
		assertEquals(KingDominoActionResult.NEXT_PLAYER, game.skip());
	}
	
	@Test
	public void endsGameOnBothLinesEmpty() {
		var players = DummyPlayerFactory.newPlayers("P1", "P2");
		var pile = DummyDominoPileFactory.newDominoPile(8, 8);
		var game = new KingDominoGame(players, pile);
		game.start();
		
		for(int i=0; i < 8; ++i) {
			game.skip();
			game.setNextDomino();
		}
		
		assertEquals(KingDominoActionResult.GAME_OVER, game.skip());
	}
}
