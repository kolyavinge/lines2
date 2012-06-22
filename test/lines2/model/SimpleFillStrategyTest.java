package lines2.model;

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

	public void setUp() {
		field = new Field(10, 10);
		fillStrategy = new SimpleFillStrategy(3);
		fillStrategy.setFillRange(left, right);
	}

	public void testFill() {
		boolean fieldOverflowFlag = false;
		int emptyCellsCount = getEmptyCellsCount();
		try {
			while (true) {
				fillStrategy.fill(field);
				int emptyCellsCountNew = getEmptyCellsCount();
				int diff = emptyCellsCount - emptyCellsCountNew;
				emptyCellsCount = emptyCellsCountNew;
				assertTrue("diff=" + Integer.toString(diff), left <= diff && diff <= right);
			}
		} catch (FieldOverflowException exp) {
			fieldOverflowFlag = true;
		}

		assertTrue(fieldOverflowFlag);
	}
}
