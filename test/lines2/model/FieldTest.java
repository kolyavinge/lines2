package lines2.model;

import java.util.Collection;
import java.util.Collections;

import junit.framework.TestCase;
import lines2.common.TestUtils;

public class FieldTest extends TestCase {

	private int rows = 10;
	private int cols = 5;

	private Field field;

	private MoveStrategy moveStrategy = new MoveStrategy() {
		public boolean checkMove(Field field, Cell startCell, Cell finishCell) {
			return true;
		}
	};

	private EraseStrategy eraseStrategy = new EraseStrategy() {
		public Collection<Cell> getErasedCells(Field field, Cell lastStepCell) {
			return Collections.emptyList();
		}
	};

	public void setUp() {
		field = new Field(rows, cols);
		field.setMoveStrategy(moveStrategy);
		field.setEraseStrategy(eraseStrategy);
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

	public void testCellExists() {
		assertTrue(field.cellExists(0, 0));
		assertFalse(field.cellExists(-1, 0));
		assertFalse(field.cellExists(0, -1));
		assertTrue(field.cellExists(rows - 1, cols - 1));
		assertFalse(field.cellExists(rows, cols));
	}
}
