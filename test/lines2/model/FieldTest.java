package lines2.model;

import junit.framework.TestCase;

public class FieldTest extends TestCase {

	private int rows = 10;
	private int cols = 5;
	private Field field;

	public void setUp() {
		field = new Field(rows, cols);
	}

	public void testFieldInit() {
		assertEquals(rows, field.getRows());
		assertEquals(cols, field.getCols());
		for (int r = 0; r < field.getRows(); r++) {
			for (int c = 0; c < field.getCols(); c++) {
				Cell cell = field.getCell(r, c);
				assertEquals(r, cell.getRow());
				assertEquals(c, cell.getCol());
				assertTrue(cell.isEmpty());
			}
		}
	}

	public void testGetCells() {
		int count = 0;
		for (Cell cell : field.getCells()) {
			assertNotNull(cell);
			count++;
		}
		assertEquals(count, rows * cols);
	}

	public void testMoveBall() {
		Cell from = field.getCell(0, 0);
		Ball ball = TestUtils.getBall();
		from.setBall(ball);
		Cell to = field.getCell(rows - 1, cols - 1);
		field.moveBall(from, to);
		assertTrue(from.isEmpty());
		assertFalse(to.isEmpty());
		assertSame(ball, to.getBall());
	}

	public void testMoveBallFromEmptyCell() {
		Cell from = field.getCell(0, 0);
		Cell to = field.getCell(rows - 1, cols - 1);
		try {
			field.moveBall(from, to);
			fail();
		} catch (IllegalArgumentException exp) {
		}
	}

	public void testMoveBallToNonEmptyCell() {
		Cell from = field.getCell(0, 0);
		Ball ball1 = TestUtils.getBall();
		from.setBall(ball1);
		Cell to = field.getCell(rows - 1, cols - 1);
		Ball ball2 = TestUtils.getBall();
		to.setBall(ball2);
		try {
			field.moveBall(from, to);
			fail();
		} catch (IllegalArgumentException exp) {
		}
	}

	public void testMoveBallToSameCell() {
		Cell from = field.getCell(0, 0);
		Cell to = field.getCell(0, 0);
		try {
			field.moveBall(from, to);
			fail();
		} catch (IllegalArgumentException exp) {
		}
	}
}
