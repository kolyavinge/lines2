package lines2.utils;

import java.util.Iterator;

public class IterableMatrix<T> implements Iterable<T> {

	private T[][] matrix;

	public IterableMatrix(T[][] matrix) {
		this.matrix = matrix;
	}

	public Iterator<T> iterator() {
		return new MatrixIterator<T>(matrix);
	}
}
