package lines2.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MatrixIterator<T> implements Iterator<T> {

	private T[][] matrix;
	private int row, col;

	public MatrixIterator(T[][] matrix) {
		if (matrix == null) {
			throw new NullPointerException();
		}
		this.matrix = matrix;
		this.row = 0;
		this.col = 0;
		boolean matrixIsEmpty = matrix.length == 1 && matrix[0].length == 0;
		if (matrixIsEmpty) {
			this.row = 1;
		}
	}

	public boolean hasNext() {
		return row < matrix.length;
	}

	public T next() {
		if (hasNext() == false) {
			throw new NoSuchElementException();
		}

		T item = matrix[row][col];
		col++;
		if (col == matrix[row].length) {
			col = 0;
			row++;
		}

		return item;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}
