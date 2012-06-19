package lines2.utils;

import java.util.NoSuchElementException;

import junit.framework.TestCase;

public class MatrixIteratorTest extends TestCase {

	public void testNonEmptyMatrix() {
		Integer[][] matrix = {
				{ 1, 2, 3 },
				{ 4, 5, 6 }
		};

		MatrixIterator<Integer> iter = new MatrixIterator<Integer>(matrix);

		assertEquals(new Integer(1), iter.next());
		assertEquals(new Integer(2), iter.next());
		assertEquals(new Integer(3), iter.next());

		assertEquals(new Integer(4), iter.next());
		assertEquals(new Integer(5), iter.next());
		assertEquals(new Integer(6), iter.next());

		assertFalse(iter.hasNext());

		try {
			iter.next();
			fail();
		} catch (NoSuchElementException exp) {
		}
	}

	public void testEmptyMatrix() {
		Integer[][] matrix = { {} };

		MatrixIterator<Integer> iter = new MatrixIterator<Integer>(matrix);

		assertFalse(iter.hasNext());

		try {
			iter.next();
			fail();
		} catch (NoSuchElementException exp) {
		}
	}

	public void testOnNullMatrix() {
		try {
			new MatrixIterator<Integer>(null);
			fail();
		} catch (NullPointerException exp) {

		}
	}
}
