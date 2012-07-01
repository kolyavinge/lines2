package lines2.model;

import java.util.ArrayList;
import java.util.Collection;
import lines2.utils.IterableMatrix;

class CellMatrix {

	private int rows, cols;
	private Cell[][] cells;

	public CellMatrix(int rows, int cols) {
		setSize(rows, cols);
		initCells();
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public Cell getCell(int row, int col) {
		try {
			return cells[row][col];
		} catch (Exception exp) {
			throw new IllegalArgumentException(String.format("No cell on [%d:%d]", row, col));
		}
	}

	public Iterable<Cell> getCells() {
		return new IterableMatrix<Cell>(cells);
	}

	public Iterable<Cell> getEmptyCells() {
		Collection<Cell> result = new ArrayList<Cell>();
		for (Cell cell : getCells()) {
			if (cell.isEmpty())
				result.add(cell);
		}

		return result;
	}

	public Iterable<Cell> getNonEmptyCells() {
		Collection<Cell> result = new ArrayList<Cell>();
		for (Cell cell : getCells()) {
			if (cell.isEmpty() == false)
				result.add(cell);
		}

		return result;
	}

	public int getCellsCount() {
		return rows * cols;
	}

	public int getNonEmptyCellsCount() {
		int count = 0;
		for (Cell c : getCells())
			if (c.isEmpty() == false)
				count++;

		return count;
	}

	public int getEmptyCellsCount() {
		int count = 0;
		for (Cell c : getCells())
			if (c.isEmpty())
				count++;

		return count;
	}

	public boolean cellExists(int row, int col) {
		return (0 <= row && row < rows) && (0 <= col && col < cols);
	}

	private void setSize(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
	}

	private void initCells() {
		cells = new Cell[rows][cols];
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				cells[r][c] = new Cell(r, c);
	}
}
