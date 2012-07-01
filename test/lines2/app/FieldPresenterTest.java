package lines2.app;

import junit.framework.TestCase;
import lines2.common.FieldStub;
import lines2.common.TestUtils;
import lines2.model.Ball;
import lines2.model.Cell;

public class FieldPresenterTest extends TestCase {

	private int fieldRows = 10, fieldCols = 20;
	private FieldStub fieldStub;
	private FieldPresenter presenter;

	public void setUp() {
		fieldStub = new FieldStub(fieldRows, fieldCols);
		presenter = new FieldPresenter(fieldStub);
	}

	public void testConstructor() {
		assertTrue(presenter.noSelectedCell());
		assertNull(presenter.getSelectedCell());
	}

	public void testSelectNonEmptyCell() {
		Ball ball = TestUtils.getBall();
		Cell cell = TestUtils.getCell(1, 2, ball);
		fieldStub.setCell(1, 2, cell);
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

	public void testSelectSameCell() {
		Ball ball = TestUtils.getBall();
		Cell cell = TestUtils.getCell(1, 2, ball);
		fieldStub.setCell(1, 2, cell);
		presenter.selectCell(1, 2);
		presenter.selectCell(1, 2);
		assertFalse(presenter.noSelectedCell());
		assertSame(cell, presenter.getSelectedCell());
	}

	public void testMoveBall() {
		Ball ball = TestUtils.getBall();
		Cell cell = TestUtils.getCell(1, 2, ball);
		fieldStub.setCell(1, 2, cell);

		presenter.selectCell(1, 2);
		assertFalse(presenter.noSelectedCell());
		assertSame(cell, presenter.getSelectedCell());

		presenter.selectCell(2, 1);
		assertTrue(presenter.noSelectedCell());
	}

	public void testMoveBallToNonEmptyCell() {
		Ball ball = TestUtils.getBall();
		Cell cell1 = TestUtils.getCell(1, 2, ball);
		fieldStub.setCell(1, 2, cell1);

		ball = TestUtils.getBall();
		Cell cell2 = TestUtils.getCell(1, 3, ball);
		fieldStub.setCell(1, 3, cell2);

		presenter.selectCell(1, 2);
		assertFalse(presenter.noSelectedCell());
		assertSame(cell1, presenter.getSelectedCell());

		presenter.selectCell(1, 3);
		assertFalse(presenter.noSelectedCell());
		assertSame(cell2, presenter.getSelectedCell());
	}

	public void testMoveBallToNonExistCell1() {
		Ball ball = TestUtils.getBall();
		Cell cell = TestUtils.getCell(1, 2, ball);
		fieldStub.setCell(1, 2, cell);
		presenter.selectCell(1, 2);
		presenter.selectCell(-1, 2);
		assertTrue(presenter.noSelectedCell());
	}

	public void testMoveBallToNonExistCell2() {
		Ball ball = TestUtils.getBall();
		Cell cell = TestUtils.getCell(1, 2, ball);
		fieldStub.setCell(1, 2, cell);
		presenter.selectCell(1, 2);
		presenter.selectCell(1, 200);
		assertTrue(presenter.noSelectedCell());
	}
}
