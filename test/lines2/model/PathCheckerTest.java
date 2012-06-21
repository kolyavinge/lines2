package lines2.model;

import junit.framework.TestCase;

public class PathCheckerTest extends TestCase {

	private static final int o = PathChecker.EMPTY;
	private static final int X = PathChecker.WALL;

	private PathChecker pathChecker;

	private int[][] getField() {
		return new int[][] {
				{ o, X, o, o, X, o, o }, // 0
				{ o, X, o, o, X, o, o }, // 1
				{ o, X, o, o, o, o, o }, // 2
				{ o, X, X, X, o, X, X }, // 3
				{ o, X, o, o, o, o, o }, // 4
				{ o, X, o, o, o, o, o }, // 5
				{ o, X, o, o, o, o, o }, // 6
		//        0  1  2  3  4  5  6
		};
	}

	public void setUp() {
		pathChecker = new PathChecker();
		pathChecker.setField(getField());
	}

	public void testGetSetStartPoint() {
		pathChecker.setStartPoint(6, 2);
		assertEquals(6, pathChecker.getStartRow());
		assertEquals(2, pathChecker.getStartCol());
	}

	public void testGetSetFinishPoint() {
		pathChecker.setFinishPoint(5, 9);
		assertEquals(5, pathChecker.getFinishRow());
		assertEquals(9, pathChecker.getFinishCol());
	}

	public void testCheck0() {
		try {
			pathChecker = new PathChecker();
			pathChecker.check();
			fail();
		} catch (IllegalArgumentException exp) {
		}
	}

	public void testCheck1() {
		pathChecker.setStartPoint(6, 2);
		pathChecker.setFinishPoint(0, 6);
		assertTrue(pathChecker.check());
	}
	
	public void testCheck5() {
		pathChecker.setStartPoint(5, 3);
		pathChecker.setFinishPoint(5, 4);
		assertTrue(pathChecker.check());
	}
	
	public void testCheck6() {
		pathChecker.setStartPoint(4, 2);
		pathChecker.setFinishPoint(6, 6);
		assertTrue(pathChecker.check());
	}

	public void testCheck4() {
		pathChecker.setStartPoint(4, 2);
		pathChecker.setFinishPoint(4, 0);
		assertFalse(pathChecker.check());
	}

	public void testCheck2() {
		pathChecker.setStartPoint(0, 0);
		pathChecker.setFinishPoint(0, 6);
		assertFalse(pathChecker.check());
	}

	public void testCheck3() {
		pathChecker.setStartPoint(0, 1);
		pathChecker.setFinishPoint(0, 0);
		assertFalse(pathChecker.check());
	}

	public void testNullField() {
		pathChecker.setField(null);
		try {
			pathChecker.check();
			fail();
		} catch (IllegalArgumentException exp) {
		}
	}

	public void testWrongStartPoint() {
		pathChecker.setStartPoint(-1, 0);
		try {
			pathChecker.check();
			fail();
		} catch (IllegalArgumentException exp) {
		}

		pathChecker.setStartPoint(0, -1);
		try {
			pathChecker.check();
			fail();
		} catch (IllegalArgumentException exp) {
		}

		pathChecker.setStartPoint(10000, 0);
		try {
			pathChecker.check();
			fail();
		} catch (IllegalArgumentException exp) {
		}

		pathChecker.setStartPoint(0, 10000);
		try {
			pathChecker.check();
			fail();
		} catch (IllegalArgumentException exp) {
		}
	}

	public void testWrongFinishPoint() {
		pathChecker.setFinishPoint(-1, 0);
		try {
			pathChecker.check();
			fail();
		} catch (IllegalArgumentException exp) {
		}

		pathChecker.setFinishPoint(0, -1);
		try {
			pathChecker.check();
			fail();
		} catch (IllegalArgumentException exp) {
		}

		pathChecker.setFinishPoint(10000, 0);
		try {
			pathChecker.check();
			fail();
		} catch (IllegalArgumentException exp) {
		}

		pathChecker.setFinishPoint(0, 10000);
		try {
			pathChecker.check();
			fail();
		} catch (IllegalArgumentException exp) {
		}
	}
}
