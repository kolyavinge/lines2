package lines2.app;

import junit.framework.TestCase;
import lines2.common.TestUtils;
import lines2.model.Ball;
import lines2.model.Cell;
import lines2.model.Field;

public class FieldPresenterTest extends TestCase {

	private int fieldRows = 10, fieldCols = 20;
	private Field field;
	private FieldPresenter presenter;

	public void setUp() {
		field = new Field(fieldRows, fieldCols);
		presenter = new FieldPresenter(field);
	}

	public void testConstructor() {
		assertSame(field, presenter.getField());
		assertTrue(presenter.noSelectedCell());
		assertNull(presenter.getSelectedCell());
	}

	public void testSelectNonEmptyCell() {
		Ball ball = TestUtils.getBall();
		Cell cell = field.getCell(1, 2);
		cell.setBall(ball);
		presenter.selectCell(1, 2);
		assertFalse(presenter.noSelectedCell());
		assertSame(cell, presenter.getSelectedCell());
	}
	
	public void testSelectEmptyCell() {
		presenter.selectCell(1, 2);
		assertTrue(presenter.noSelectedCell());
	}
	
	public void testSelectNonExistCell() {
		presenter.selectCell(-1, 2);
		assertTrue(presenter.noSelectedCell());
		presenter.selectCell(1, 20000);
		assertTrue(presenter.noSelectedCell());
	}
	
	public void testMoveBall() {
		Ball ball = TestUtils.getBall();
		Cell cell = field.getCell(1, 2);
		cell.setBall(ball);
		
		presenter.selectCell(1, 2);
		assertFalse(presenter.noSelectedCell());
		assertSame(cell, presenter.getSelectedCell());
		
		presenter.selectCell(2, 1);
		assertTrue(presenter.noSelectedCell());
	}
	
	public void testMoveBallToNonExistCell1() {
		Ball ball = TestUtils.getBall();
		Cell cell = field.getCell(1, 2);
		cell.setBall(ball);
		presenter.selectCell(1, 2);
		presenter.selectCell(-1, 2);
		assertTrue(presenter.noSelectedCell());
	}
	
	public void testMoveBallToNonExistCell2() {
		Ball ball = TestUtils.getBall();
		Cell cell = field.getCell(1, 2);
		cell.setBall(ball);
		presenter.selectCell(1, 2);
		presenter.selectCell(1, 200);
		assertTrue(presenter.noSelectedCell());
	}
}
