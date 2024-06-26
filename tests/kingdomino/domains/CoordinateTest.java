package kingdomino.domains;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CoordinateTest {
	@Test
	public void initsWithRowAndCol() {
		var coord = Coordinate.ofRowCol(4, 2);
		
		assertEquals(4, coord.getRow());
		assertEquals(2, coord.getCol());
		assertEquals("Coord(row: 4, col: 2)", coord.toString());
	}
	
	@Test
	public void translatesFromItself() {
		var start = Coordinate.ofRowCol(4, 4);
		
		var translation = start.translate(0, -1);
		
		assertEquals(Coordinate.ofRowCol(4, 3), translation);
	}
	
	@Test
	public void chainsTranslations() {
		var start = Coordinate.ofRowCol(4, 4);
		
		var translation = start.translate(0, -1).translate(-1, 0).translate(0, 1).translate(1, 0);
		
		assertEquals(translation, start);
	}
	
	@Test
	public void rotatesFromOriginCounterClockWise() {
		var start = Coordinate.ofRowCol(-5, 5);
		
		var rotation = start.rotate90From(Coordinate.ZERO);
		
		assertEquals(Coordinate.ofRowCol(-5, -5), rotation);
	}
	
	@Test
	public void chainsRotations() {
		var start = Coordinate.ofRowCol(4, 4);
		
		var rotations = start
				.rotate90From(Coordinate.ZERO)
				.rotate90From(Coordinate.ZERO)
				.rotate90From(Coordinate.ZERO)
				.rotate90From(Coordinate.ZERO);
		
		assertEquals(rotations, start);
	}
	
	@Test
	public void deducesHisNeighbours() {
		var coord = Coordinate.ofRowCol(4, 2);
		
		var actualNeighbors = coord.getNeighbors();

		assertEquals(4, actualNeighbors.size());
		assertTrue(actualNeighbors.contains(Coordinate.ofRowCol(3, 2)));
		assertTrue(actualNeighbors.contains(Coordinate.ofRowCol(5, 2)));
		assertTrue(actualNeighbors.contains(Coordinate.ofRowCol(4, 1)));
		assertTrue(actualNeighbors.contains(Coordinate.ofRowCol(4, 3)));
	}
	
	@Test
	public void isEquatable() {
		//Identity equality
		assertEquals(Coordinate.ZERO, Coordinate.ZERO);
		
		//Value equality
		assertEquals(Coordinate.ofRowCol(2, 4), Coordinate.ofRowCol(2, 4));
		
		//Type inequality
		assertNotEquals(Coordinate.ofRowCol(4, 2), "Coord(row: 4, col: 2)");
		
		//Inequality on rows and cols
		assertNotEquals(Coordinate.ofRowCol(4, 2), Coordinate.ofRowCol(2, 4));
		
		//Inequality on rows only
		assertNotEquals(Coordinate.ofRowCol(4, 2), Coordinate.ofRowCol(5, 2));
		
		//Inequality on cols only
		assertNotEquals(Coordinate.ofRowCol(4, 2), Coordinate.ofRowCol(4, 3));
	}
	
	@Test
	public void fullfillsHashCodeContract()  {
		assertEquals(Coordinate.ofRowCol(2, 4).hashCode(), Coordinate.ofRowCol(2, 4).hashCode());
	}
}
