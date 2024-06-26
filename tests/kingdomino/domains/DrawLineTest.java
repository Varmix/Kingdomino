package kingdomino.domains;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import kingdomino.domains.dummies.DummyDominoFactory;

public class DrawLineTest {
	@Test
	public void initsWithDominoes() {
		List<Domino> dominoes = List.of(
				DummyDominoFactory.createDomino(1),
				DummyDominoFactory.createDomino(2),
				DummyDominoFactory.createDomino(3)
		);
		var line = new DrawLine(dominoes);
		
		assertEquals(3, line.getCount());
		assertEquals(3, line.getFreeDominosCount());
		assertEquals("#ffffff", line.getColorFor(dominoes.get(0)));
		assertEquals(dominoes.get(0), line.getTopDomino());
		assertEquals(Player.UNKNOWN, line.getTopPlayer());
	}
	
	@Test
	public void initsWithUndefinedDominoes() {
		var line = new DrawLine(null);
		
		assertEquals(0, line.getCount());
		assertEquals(0, line.getFreeDominosCount());
		assertEquals("#ffffff", line.getColorFor(Domino.UNKNOWN));
		assertEquals(Domino.UNKNOWN, line.getTopDomino());
		assertEquals(Player.UNKNOWN, line.getTopPlayer());
	}
	
	@Test
	public void initsWithNoDomino() {
		List<Domino> noDomino = List.of();
		var line = new DrawLine(noDomino);
		
		assertEquals(0, line.getCount());
		assertEquals(0, line.getFreeDominosCount());
		assertEquals("#ffffff", line.getColorFor(null));
		assertEquals(Domino.UNKNOWN, line.getTopDomino());
		assertEquals(Player.UNKNOWN, line.getTopPlayer());
	}
	
	@Test
	public void affectsAllDominoesToPlayers() {
		List<Domino> dominoes = List.of(
				DummyDominoFactory.createDomino(1),
				DummyDominoFactory.createDomino(2),
				DummyDominoFactory.createDomino(3)
		);
		var line = new DrawLine(dominoes);
		List<Player> players = List.of(
			new Player("HenNi", "#ff0000"),
			new Player("MatCh", "#00ff00"),
			new Player("JadJe", "#0000ff")
		);
		
		line.affectRandomly(players);
		
		assertEquals(0, line.getFreeDominosCount());
		assertTrue(players.contains(line.getTopPlayer()));
	}
	
	@Test
	public void affectsNoDominoeOnNull() {
		List<Domino> dominoes = List.of(
				DummyDominoFactory.createDomino(1),
				DummyDominoFactory.createDomino(2),
				DummyDominoFactory.createDomino(3)
		);
		var line = new DrawLine(dominoes);

		line.affectRandomly(null);
		
		assertEquals(3, line.getFreeDominosCount());
	}
	
	@Test
	public void affectsNoDominoeOnEmpty() {
		List<Domino> dominoes = List.of(
				DummyDominoFactory.createDomino(1),
				DummyDominoFactory.createDomino(2),
				DummyDominoFactory.createDomino(3)
		);
		var line = new DrawLine(dominoes);

		line.affectRandomly(List.of());
		
		assertEquals(3, line.getFreeDominosCount());
	}
	
	@Test
	public void affectsSpecificPlayerToDomino() {
		List<Domino> dominoes = List.of(
				DummyDominoFactory.createDomino(1),
				DummyDominoFactory.createDomino(2),
				DummyDominoFactory.createDomino(3)
		);
		
		var line = new DrawLine(dominoes);
		
		line.affectDominoToPlayer(dominoes.get(1), new Player("D1", "#aabbcc"));
		
		assertEquals("#aabbcc", line.getColorFor(dominoes.get(1)));
		assertEquals(2, line.getFreeDominosCount());
		assertEquals(3, line.getCount());
		assertEquals(Player.UNKNOWN.getHexColor(), line.getColorFor(dominoes.get(0)));
		assertEquals(Player.UNKNOWN.getHexColor(), line.getColorFor(dominoes.get(2)));
	}
	
	@Test
	public void removesTopDomino() {
		List<Domino> dominoes = List.of(
				DummyDominoFactory.createDomino(1),
				DummyDominoFactory.createDomino(2),
				DummyDominoFactory.createDomino(3)
		);
		
		var line = new DrawLine(dominoes);
		
		line.removeFirst();
		
		assertEquals(dominoes.get(1), line.getTopDomino());
		assertEquals(2, line.getCount());
	}
	
	@Test
	public void findsNextFreeDomino() {
		List<Domino> dominoes = List.of(
				DummyDominoFactory.createDomino(1),
				DummyDominoFactory.createDomino(2),
				DummyDominoFactory.createDomino(3),
				DummyDominoFactory.createDomino(4)
		);
		var line = new DrawLine(dominoes);
		line.affectDominoToPlayer(dominoes.get(1), new Player("D1", "#aabbcc"));
		
		assertEquals(dominoes.get(2), line.getNextFreeFrom(dominoes.get(1)));
		assertEquals(dominoes.get(3), line.getNextFreeFrom(dominoes.get(2)));
		assertEquals(dominoes.get(0), line.getNextFreeFrom(dominoes.get(3)));
	}
	
	@Test
	public void findsNoDominoOnAllDominoesSet() {
		List<Domino> dominoes = List.of(
				DummyDominoFactory.createDomino(1),
				DummyDominoFactory.createDomino(2),
				DummyDominoFactory.createDomino(3)
		);
		var line = new DrawLine(dominoes);
		line.affectDominoToPlayer(dominoes.get(0), new Player("D1", "#aabbcc"));
		line.affectDominoToPlayer(dominoes.get(1), new Player("D2", "#aabbcc"));
		line.affectDominoToPlayer(dominoes.get(2), new Player("D3", "#aabbcc"));
		
		assertEquals(Domino.UNKNOWN, line.getNextFreeFrom(dominoes.get(0)));
		assertEquals(Domino.UNKNOWN, line.getNextFreeFrom(dominoes.get(1)));
		assertEquals(Domino.UNKNOWN, line.getNextFreeFrom(dominoes.get(2)));
	}
	
}
