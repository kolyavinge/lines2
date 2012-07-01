package lines2.common;

import lines2.model.Cell;
import lines2.model.Field;

public class FieldStub extends Field {

	private Cell[][] matrix;

	public FieldStub(int rows, int cols) {
		super(rows, cols);
		matrix = new Cell[rows][cols];
	}

	public void setCell(int row, int col, Cell cell) {
		matrix[row][col] = cell;
	}

	@Override
	public Cell getCell(int row, int col) {
		if (matrix[row][col] != null)
			return matrix[row][col];
		else
			return super.getCell(row, col);
	}
};
