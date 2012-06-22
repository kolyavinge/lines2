package lines2.model;

import java.util.*;

import lines2.utils.Point;

class Eraser {

	public static int EMPTY = -1;

	private int minEraseLineLength;
	private int[][] field;
	private int lastStepRow, lastStepCol;

	public Collection<Point> getErasedCells() {
		verifyLastStepPoint();

		Collection<Point> result = new ArrayList<Point>(2 * minEraseLineLength);

		if (!isEmpty(lastStepRow, lastStepCol)) {
			Collection<Point> horizontal = getHorizontalErasedCells();
			Collection<Point> vertical = getVerticalErasedCells();

			if (!horizontal.isEmpty() && !vertical.isEmpty())
				vertical.remove(new Point(lastStepCol, lastStepRow));

			result.addAll(horizontal);
			result.addAll(vertical);
		}

		return result;
	}

	private Collection<Point> getHorizontalErasedCells() {
		int row = lastStepRow, col = lastStepCol;
		while (cellExists(row, col - 1) && isSame(row, col - 1, lastStepRow, lastStepCol))
			col--;

		int lastCol = col;

		while (cellExists(row, col + 1) && isSame(row, col + 1, lastStepRow, lastStepCol))
			col++;

		Collection<Point> result = new ArrayList<Point>(minEraseLineLength);
		if (col - lastCol + 1 >= minEraseLineLength) {
			while (lastCol <= col) {
				result.add(new Point(lastCol, lastStepRow));
				lastCol++;
			}
		}

		return result;
	}

	private Collection<Point> getVerticalErasedCells() {
		int row = lastStepRow, col = lastStepCol;
		while (cellExists(row - 1, col) && isSame(row - 1, col, lastStepRow, lastStepCol))
			row--;

		int lastRow = row;

		while (cellExists(row + 1, col) && isSame(row + 1, col, lastStepRow, lastStepCol))
			row++;

		Collection<Point> result = new ArrayList<Point>(minEraseLineLength);
		if (row - lastRow + 1 >= minEraseLineLength) {
			while (lastRow <= row) {
				result.add(new Point(lastStepCol, lastRow));
				lastRow++;
			}
		}

		return result;
	}

	private boolean isEmpty(int row, int col) {
		return field[row][col] == EMPTY;
	}

	private boolean isSame(int row1, int col1, int row2, int col2) {
		return field[row1][col1] == field[row2][col2];
	}

	private boolean cellExists(int row, int col) {
		return (0 <= row) && (row < field.length) && (0 <= col) && (col < field[row].length);
	}

	public int getMinEraseLineLength() {
		return minEraseLineLength;
	}

	public void setMinEraseLineLength(int eraseLineLength) {
		this.minEraseLineLength = eraseLineLength;
	}

	public int[][] getField() {
		return field;
	}

	public void setField(int[][] field) {
		this.field = field;
	}

	public int getLastStepRow() {
		return lastStepRow;
	}

	public int getLastStepCol() {
		return lastStepCol;
	}

	public void setLastStepPoint(int row, int col) {
		lastStepRow = row;
		lastStepCol = col;
	}

	private void verifyLastStepPoint() {
		if (cellExists(lastStepRow, lastStepCol) == false)
			throw new IllegalArgumentException();
	}
}
