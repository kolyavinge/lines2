package lines2.model;

import java.util.Collection;

import junit.framework.TestCase;
import lines2.utils.Point;

public class EraserTest extends TestCase {

	private int o = Eraser.EMPTY;
	private Eraser eraser;
	private Collection<Point> erasedCells;

	private int[][] getField() {
		return new int[][] {
				{ 1, 1, 1, 1, 1, 1, 1 },
				{ o, o, 2, o, o, 1, o },
				{ o, 1, 2, 1, o, 1, o },
				{ o, 1, 2, o, o, 1, o },
				{ 3, 1, 2, o, o, 1, o },
		};
	}

	private void getErasedCells() {
		erasedCells = eraser.getErasedCells();
	}

	private void resultSize(int value) {
		assertEquals(value, erasedCells.size());
	}

	private void resultContainsPoint(int x, int y) {
		assertTrue(erasedCells.contains(new Point(x, y)));
	}

	public void setUp() {
		eraser = new Eraser();
		eraser.setField(getField());
	}

	public void testGetErasedCells0() {
		eraser.setMinEraseLineLength(1);
		eraser.setLastStepPoint(4, 0);
		getErasedCells();
		resultSize(1);
		resultContainsPoint(0, 4);
	}

	public void testGetErasedCells1() {
		eraser.setMinEraseLineLength(5);
		eraser.setLastStepPoint(0, 3);
		getErasedCells();
		resultSize(7);
		resultContainsPoint(0, 0);
		resultContainsPoint(1, 0);
		resultContainsPoint(2, 0);
		resultContainsPoint(3, 0);
		resultContainsPoint(4, 0);
		resultContainsPoint(5, 0);
		resultContainsPoint(6, 0);
	}

	public void testGetErasedCells2() {
		eraser.setMinEraseLineLength(4);
		eraser.setLastStepPoint(2, 2);
		getErasedCells();
		resultSize(4);
		resultContainsPoint(2, 1);
		resultContainsPoint(2, 2);
		resultContainsPoint(2, 3);
		resultContainsPoint(2, 4);
	}

	public void testGetErasedCells3() {
		eraser.setMinEraseLineLength(5);
		eraser.setLastStepPoint(0, 5);
		getErasedCells();
		resultSize(11);

		resultContainsPoint(0, 0);
		resultContainsPoint(1, 0);
		resultContainsPoint(2, 0);
		resultContainsPoint(3, 0);
		resultContainsPoint(4, 0);
		resultContainsPoint(5, 0);
		resultContainsPoint(6, 0);

		resultContainsPoint(5, 1);
		resultContainsPoint(5, 2);
		resultContainsPoint(5, 3);
		resultContainsPoint(5, 4);
	}

	public void testGetErasedCells4() {
		eraser.setMinEraseLineLength(2);
		eraser.setLastStepPoint(1, 0);
		getErasedCells();
		resultSize(0);
	}

	public void testLastStepPointNotExists() {
		eraser.setMinEraseLineLength(5);
		
		eraser.setLastStepPoint(-1, 0);
		try {
			eraser.getErasedCells();
			fail();
		} catch (IllegalArgumentException exp) {
		}
		
		eraser.setLastStepPoint(0, -1);
		try {
			eraser.getErasedCells();
			fail();
		} catch (IllegalArgumentException exp) {
		}
		
		eraser.setLastStepPoint(10000, 0);
		try {
			eraser.getErasedCells();
			fail();
		} catch (IllegalArgumentException exp) {
		}
		
		eraser.setLastStepPoint(0, 10000);
		try {
			eraser.getErasedCells();
			fail();
		} catch (IllegalArgumentException exp) {
		}
	}
}
