package lines2.model;

import java.util.*;
import junit.framework.TestCase;

public class SimpleFillStrategyTest extends TestCase {

	private Field field;
	private int left = 2, right = 5;
	private SimpleFillStrategy fillStrategy;

	private int getEmptyCellsCount() {
		int count = 0;
		for (Cell c : field.getCells())
			if (c.isEmpty())
				count++;

		return count;
	}

	private void fillCells() {
		Map<Cell, Ball> nextCells = fillStrategy.getNextFillCells(field.getCells());
		for (Map.Entry<Cell, Ball> kv : nextCells.entrySet()) {
			Cell cell = kv.getKey();
			Ball ball = kv.getValue();
			cell.setBall(ball);
		}
	}
	
	public void setUp() {
		field = new Field(10, 10);
		fillStrategy = new SimpleFillStrategy(3);
		fillStrategy.setFillRange(left, right);
	}

	public void testFill() {
		int emptyCellsCount = field.getRows() * field.getCols();
		while (getEmptyCellsCount() > 0) {
			fillCells();
			int emptyCellsCountNew = getEmptyCellsCount();
			int diff = emptyCellsCount - emptyCellsCountNew;
			emptyCellsCount = emptyCellsCountNew;
			assertTrue("diff=" + Integer.toString(diff), diff <= right);
		}
	}
}
