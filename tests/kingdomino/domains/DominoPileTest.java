package kingdomino.domains;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.*;

import kingdomino.domains.dummies.DummyDominoFactory;

public class DominoPileTest {
	private List<Domino> dominoes;
	
	@BeforeEach
	public void setup() {
		dominoes = List.of(
				DummyDominoFactory.createDomino(1),
				DummyDominoFactory.createDomino(2),
				DummyDominoFactory.createDomino(3),
				DummyDominoFactory.createDomino(4),
				DummyDominoFactory.createDomino(5),
				DummyDominoFactory.createDomino(6),
				DummyDominoFactory.createDomino(8),
				DummyDominoFactory.createDomino(9),
				DummyDominoFactory.createDomino(10)
		);
	}
	
	@Test
	public void producesSubCollectionOfDominoes() {
		var pile = new DominoPile(dominoes, 9);
		
		var actualSub = pile.next(3);
		
		assertSubCollection(actualSub, 3);
	}
	
	@Test
	public void hasNoNextOnEmptyCollection() {
		var emptyPile = new DominoPile(List.of(), 9);		
		
		var actualSub = emptyPile.next(3);
		
		assertSubCollection(actualSub, 0);
	}
	
	@Test
	public void producesSubCollectionsOfDominoes() {
		var pile = new DominoPile(dominoes, 9);
		
		var firstSub = pile.next(3);
		var secondSub = pile.next(3);
		var thirdSub = pile.next(3);
		
		assertSubCollection(firstSub, 3);
		assertSubCollection(secondSub, 3);
		assertSubCollection(thirdSub, 3);
	}
	
	private void assertSubCollection(Collection<Domino> actualSub, int expectedSize) {
		assertEquals(expectedSize, actualSub.size());
		assertEquals(expectedSize, Set.copyOf(actualSub).size());
		for(var subDomino : actualSub) {
			assertTrue(dominoes.contains(subDomino));
		}
	}
	
	@Test
	public void producesSubCollectionsOnNoDominoesLeft() {
		var pile = new DominoPile(dominoes, 9);
		
		pile.next(3);
		pile.next(3);
		pile.next(3);
		var fourthSub = pile.next(3);
		
		assertIterableEquals(List.of(), fourthSub);
	}
}
