package lines2.model;

import java.util.Iterator;

import junit.framework.TestCase;
import lines2.common.TestUtils;

public class CellMatrixTest extends TestCase {

	private int rows = 10;
	private int cols = 5;
	private CellMatrix cellMatrix;

	public void setUp() {
		cellMatrix = new CellMatrix(rows, cols);
	}

	public void testGetRowsAndCols() {
		assertEquals(rows, cellMatrix.getRows());
		assertEquals(cols, cellMatrix.getCols());
	}

	public void testGetCellsCount() {
		assertEquals(rows * cols, cellMatrix.getCellsCount());
	}

	public void testFieldInit() {
		for (int r = 0; r < cellMatrix.getRows(); r++) {
			for (int c = 0; c < cellMatrix.getCols(); c++) {
				Cell cell = cellMatrix.getCell(r, c);
				assertEquals(r, cell.getRow());
				assertEquals(c, cell.getCol());
				assertTrue(cell.isEmpty());
			}
		}
	}

	public void testGetCells() {
		int count = 0;
		for (Cell cell : cellMatrix.getCells()) {
			assertNotNull(cell);
			count++;
		}
		assertEquals(count, rows * cols);
	}

	public void testGetNonEmptyCells() {
		Cell cell1 = cellMatrix.getCell(0, 1);
		Cell cell2 = cellMatrix.getCell(1, 2);
		cell1.setBall(TestUtils.getBall());
		cell2.setBall(TestUtils.getBall());

		Iterator<Cell> iter = cellMatrix.getNonEmptyCells().iterator();

		assertTrue(iter.hasNext());
		assertSame(cell1, iter.next());
		assertTrue(iter.hasNext());
		assertSame(cell2, iter.next());
		assertFalse(iter.hasNext());
	}

	public void testGetEmptyCells() {
		Cell cell = cellMatrix.getCell(0, 1);
		cell.setBall(TestUtils.getBall());
		for (Cell emptyCell : cellMatrix.getEmptyCells()) {
			assertTrue(emptyCell != cell);
			assertTrue(emptyCell.isEmpty());
		}
	}
	
	public void testGetNonEmptyCellsCount() {
		assertEquals(0, cellMatrix.getNonEmptyCellsCount());
		Cell cell = cellMatrix.getCell(0, 1);
		cell.setBall(TestUtils.getBall());
		assertEquals(1, cellMatrix.getNonEmptyCellsCount());
	}

	public void testGetEmptyCellsCount() {
		assertEquals(rows * cols, cellMatrix.getEmptyCellsCount());
		Cell cell = cellMatrix.getCell(0, 1);
		cell.setBall(TestUtils.getBall());
		assertEquals(rows * cols - 1, cellMatrix.getEmptyCellsCount());
	}
}
