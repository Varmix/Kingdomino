package kingdomino.domains;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BoundingBoxTest {
	@Test
	public void initsWithAnOrigin() {
		BoundingBox bb = BoundingBox.ofCoord(Coordinate.ofRowCol(4, 4));
		
		assertEquals(1, bb.getHeight());
		assertEquals(1, bb.getWidth());
	}
	
	@Test
	public void extendsInWidthWithNull() {
		BoundingBox bb = BoundingBox.ofCoord(null);
		

		assertEquals(1, bb.getHeight());
		assertEquals(1, bb.getWidth());
	}
	
	@Test
	public void simulatesHeightWithCoord() {
		BoundingBox bb = BoundingBox.ofCoord(Coordinate.ofRowCol(4, 4));
		
		assertEquals(3, bb.getHeightExtendedBy(Coordinate.ofRowCol(2,4)));
		assertEquals(3, bb.getHeightExtendedBy(Coordinate.ofRowCol(6, 4)));
	}

	@Test
	public void simulatesWithWithCoord() {
		BoundingBox bb = BoundingBox.ofCoord(Coordinate.ofRowCol(4, 4));
		
		assertEquals(4, bb.getWidthExtendedBy(Coordinate.ofRowCol(4, 7)));
		assertEquals(4, bb.getWidthExtendedBy(Coordinate.ofRowCol(4, 1)));
	}
	
	
	@Test
	public void extendsInHeight() {
		BoundingBox bb = BoundingBox.ofCoord(Coordinate.ofRowCol(4, 4));
		bb.extendWith(Coordinate.ofRowCol(2,4));
		bb.extendWith(Coordinate.ofRowCol(6, 4));
		
		assertEquals(5, bb.getHeight());
	}
	
	@Test
	public void extendsInWidth() {
		BoundingBox bb = BoundingBox.ofCoord(Coordinate.ofRowCol(4, 4));
		bb.extendWith(Coordinate.ofRowCol(4, 3));
		bb.extendWith(Coordinate.ofRowCol(4, 5));
		
		assertEquals(3, bb.getWidth());
	}
	

	@Test
	public void extendsInWidthAndHeightFromTopLeft() {
		BoundingBox bb = BoundingBox.ofCoord(Coordinate.ofRowCol(4, 4));
		bb.extendWith(Coordinate.ofRowCol(1, 0));
		
		assertEquals(4, bb.getHeight());
		assertEquals(5, bb.getWidth());
	}
	
	@Test
	public void extendsInWidthAndHeightFromBottomRight() {
		BoundingBox bb = BoundingBox.ofCoord(Coordinate.ofRowCol(4, 4));
		bb.extendWith(Coordinate.ofRowCol(9, 10));
		
		assertEquals(6, bb.getHeight());
		assertEquals(7, bb.getWidth());
	}
}
