package lines2.model;

import static lines2.model.Color.RED;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import junit.framework.TestCase;
import lines2.common.TestUtils;

public class FieldTest extends TestCase {

	private int rows = 10;
	private int cols = 5;
	private Field field;
	private MoveStrategy moveStrategy = TestUtils.getMoveStrategyStub();
	private EraseStrategy eraseStrategy = TestUtils.getEraseStrategyStub();
	private FillStrategy fillStrategy = TestUtils.getFillStrategyStub();

	public void setUp() {
		field = new Field(rows, cols);
		field.setMoveStrategy(moveStrategy);
		field.setEraseStrategy(eraseStrategy);
		field.setFillStrategy(fillStrategy);
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

	public void testGetNonEmptyCells() {
		Cell cell1 = field.getCell(0, 1);
		Cell cell2 = field.getCell(1, 2);
		cell1.setBall(TestUtils.getBall());
		cell2.setBall(TestUtils.getBall());
		Iterator<Cell> iter = field.getNonEmptyCells().iterator();
		assertTrue(iter.hasNext());
		assertSame(cell1, iter.next());
		assertTrue(iter.hasNext());
		assertSame(cell2, iter.next());
		assertFalse(iter.hasNext());
	}
	
	public void testGetEmptyCells() {
		Cell cell = field.getCell(0, 1);
		cell.setBall(TestUtils.getBall());
		for (Cell emptyCell : field.getEmptyCells()) {
			assertTrue(emptyCell != cell);
			assertTrue(emptyCell.isEmpty());
		}
	}
	
	public void testGetEmptyCellsCount() {
		assertEquals(rows * cols, field.getEmptyCellsCount());
		Cell cell = field.getCell(0, 1);
		cell.setBall(TestUtils.getBall());
		assertEquals(rows * cols - 1, field.getEmptyCellsCount());
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

	public void testEraseAfterFill() {
		field.setEraseStrategy(new StraightEraseStrategy(4));

		FillStrategy fillStrategy = new FillStrategy() {
			public Collection<Ball> getNextBalls(Iterable<Cell> cells) {
				Collection<Ball> result = new ArrayList<Ball>();
				Cell cell = field.getCell(0, 0);
				Ball ball = TestUtils.getColoredBall(RED);
				ball.setCell(cell);
				result.add(ball);

				return result;
			}
		};

		field.getCell(0, 1).setBall(TestUtils.getColoredBall(RED));
		field.getCell(0, 2).setBall(TestUtils.getColoredBall(RED));
		field.setFillStrategy(fillStrategy);
		field.getCell(0, 4).setBall(TestUtils.getColoredBall(RED));

		field.moveBall(field.getCell(0, 4), field.getCell(0, 3));

		assertFalse(field.getCell(0, 0).isEmpty());
		assertTrue(field.getCell(0, 1).isEmpty());
		assertTrue(field.getCell(0, 2).isEmpty());
		assertTrue(field.getCell(0, 3).isEmpty());
	}

	public void testFillBusyCell() {
		FillStrategy fillStrategy = new FillStrategy() {
			public Collection<Ball> getNextBalls(Iterable<Cell> cells) {
				Collection<Ball> result = new ArrayList<Ball>();
				Cell cell = field.getCell(0, 0);
				Ball ball = TestUtils.getColoredBall(RED);
				ball.setCell(cell);
				result.add(ball);

				return result;
			}
		};
		field.setFillStrategy(fillStrategy);

		Ball ball = TestUtils.getColoredBall(RED);
		field.getCell(0, 0).setBall(ball);
		field.getCell(0, 1).setBall(TestUtils.getColoredBall(RED));
		field.moveBall(field.getCell(0, 1), field.getCell(0, 2));

		assertFalse(field.getCell(0, 0).isEmpty());
		assertSame(field.getCell(0, 0).getBall(), ball);
	}

	public void testFillAfterTotalErase() {
		field.setEraseStrategy(new StraightEraseStrategy(2));

		FillStrategy fillStrategy = new FillStrategy() {
			public Collection<Ball> getNextBalls(Iterable<Cell> cells) {
				Collection<Ball> result = new ArrayList<Ball>();
				Cell cell = field.getCell(0, 0);
				Ball ball = TestUtils.getColoredBall(RED);
				ball.setCell(cell);
				result.add(ball);

				return result;
			}
		};
		field.setFillStrategy(fillStrategy);

		field.getCell(0, 0).setBall(TestUtils.getColoredBall(RED));
		field.getCell(0, 2).setBall(TestUtils.getColoredBall(RED));
		field.moveBall(field.getCell(0, 2), field.getCell(0, 1));

		assertFalse(field.getCell(0, 0).isEmpty());
	}
}
