package lines2.model;

import java.util.Collection;

import junit.framework.TestCase;
import lines2.common.TestUtils;

public class StraightEraseStrategyTest extends TestCase {

	private int fieldRows = 20, fieldCols = 20;
	private Field field;
	private int eraseLineLength = 5;
	private StraightEraseStrategy eraseStrategy;
	
	public void setUp() {
		field = new Field(fieldRows, fieldCols);
		eraseStrategy = new StraightEraseStrategy(eraseLineLength);
	}
	
	public void testErase() {
		Ball ball = TestUtils.getColoredBall(Color.RED);
		
		field.getCell(0, 0).setBall(ball);
		field.getCell(0, 1).setBall(ball);
		field.getCell(0, 2).setBall(ball);
		field.getCell(0, 3).setBall(ball);
		field.getCell(0, 4).setBall(ball);
		field.getCell(0, 5).setBall(ball);
		
		field.getCell(1, 1).setBall(ball);
		field.getCell(2, 1).setBall(ball);
		field.getCell(3, 1).setBall(ball);
		field.getCell(4, 1).setBall(ball);
		field.getCell(5, 1).setBall(ball);
		field.getCell(6, 1).setBall(ball);
		field.getCell(7, 1).setBall(ball);
		
		Cell lastStepCell = field.getCell(0, 1);
		Collection<Cell> eraseCells = eraseStrategy.getErasedCells(field, lastStepCell);
		
		assertEquals(13, eraseCells.size());
		
		assertTrue(eraseCells.contains(field.getCell(0, 0)));
		assertTrue(eraseCells.contains(field.getCell(0, 1)));
		assertTrue(eraseCells.contains(field.getCell(0, 2)));
		assertTrue(eraseCells.contains(field.getCell(0, 3)));
		assertTrue(eraseCells.contains(field.getCell(0, 4)));
		assertTrue(eraseCells.contains(field.getCell(0, 5)));
		
		assertTrue(eraseCells.contains(field.getCell(1, 1)));
		assertTrue(eraseCells.contains(field.getCell(2, 1)));
		assertTrue(eraseCells.contains(field.getCell(3, 1)));
		assertTrue(eraseCells.contains(field.getCell(4, 1)));
		assertTrue(eraseCells.contains(field.getCell(5, 1)));
		assertTrue(eraseCells.contains(field.getCell(6, 1)));
		assertTrue(eraseCells.contains(field.getCell(7, 1)));
	}
}
