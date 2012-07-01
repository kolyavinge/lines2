package lines2.model;

import static lines2.model.Color.RED;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;
import lines2.common.TestUtils;

public class FieldTest extends TestCase {

	private int rows = 10;
	private int cols = 5;
	private Field field;
	private MoveStrategy moveStrategy = TestUtils.getMoveStrategyStub();
	private EraseStrategy eraseStrategy = TestUtils.getEraseStrategyStub();
	private FillStrategy fillStrategy = TestUtils.getFillStrategyStub();

	private boolean isEmptyCell(int row, int col) {
		return field.getCell(row, col).isEmpty();
	}

	private void setBallToField(int row, int col, Ball ball) {
		field.getCell(row, col).setBall(ball);
	}

	public void setUp() {
		field = new Field(rows, cols);
		field.setMoveStrategy(moveStrategy);
		field.setEraseStrategy(eraseStrategy);
		field.setFillStrategy(fillStrategy);
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
		} catch (MoveBallException exp) {
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
		} catch (MoveBallException exp) {
		}
	}

	public void testMoveBallToSameCell() {
		Cell from = field.getCell(0, 0);
		Cell to = field.getCell(0, 0);
		try {
			field.moveBall(from, to);
			fail();
		} catch (MoveBallException exp) {
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

		setBallToField(0, 1, TestUtils.getColoredBall(RED));
		setBallToField(0, 2, TestUtils.getColoredBall(RED));
		field.setFillStrategy(fillStrategy);
		setBallToField(0, 4, TestUtils.getColoredBall(RED));

		field.moveBall(field.getCell(0, 4), field.getCell(0, 3));

		assertFalse(isEmptyCell(0, 0));
		assertTrue(isEmptyCell(0, 1));
		assertTrue(isEmptyCell(0, 2));
		assertTrue(isEmptyCell(0, 3));
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
		setBallToField(0, 0, ball);
		setBallToField(0, 1, TestUtils.getColoredBall(RED));
		field.moveBall(field.getCell(0, 1), field.getCell(0, 2));

		assertFalse(isEmptyCell(0, 0));
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

		setBallToField(0, 0, TestUtils.getColoredBall(RED));
		setBallToField(0, 2, TestUtils.getColoredBall(RED));

		field.moveBall(field.getCell(0, 2), field.getCell(0, 1));

		assertFalse(field.getCell(0, 0).isEmpty());
	}

	public void testFieldIsFull() {
		for (Cell cell : field.getCells())
			cell.setBall(TestUtils.getBall());

		field.getCell(0, 0).clear();

		FillStrategy fillStrategy = new FillStrategy() {
			public Collection<Ball> getNextBalls(Iterable<Cell> cells) {
				Collection<Ball> result = new ArrayList<Ball>();
				Cell cell = field.getCell(0, 1);
				Ball ball = TestUtils.getColoredBall(RED);
				ball.setCell(cell);
				result.add(ball);

				return result;
			}
		};
		field.setFillStrategy(fillStrategy);

		try {
			field.moveBall(field.getCell(0, 1), field.getCell(0, 0));
			fail();
		} catch (FieldOverflowException exp) {
		}
	}
}
