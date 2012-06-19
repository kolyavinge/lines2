package lines2.model;

import junit.framework.TestCase;

public class CellTest extends TestCase {

	private Cell cell;

	public void setUp() {
		cell = new Cell();
	}

	public void testCell() {
		assertEquals(0, cell.getRow());
		assertEquals(0, cell.getCol());
		assertTrue(cell.isEmpty());
	}

	public void testCell2() {
		cell = new Cell(2, 9);
		assertEquals(2, cell.getRow());
		assertEquals(9, cell.getCol());
		assertTrue(cell.isEmpty());
	}

	public void testSetPosition() {
		cell.setPosition(5, 8);
		assertEquals(5, cell.getRow());
		assertEquals(8, cell.getCol());
	}

	public void testSetBall() {
		Ball ball = TestUtils.getBall();
		cell.setBall(ball);
		assertSame(ball, cell.getBall());
	}

	public void testClearBall() {
		Ball ball = TestUtils.getBall();
		cell.setBall(ball);
		assertFalse(cell.isEmpty());
		cell.clearBall();
		assertTrue(cell.isEmpty());
	}
}
