package lines2.model;

import java.util.*;
import junit.framework.TestCase;

public class ChangeManagerTest extends TestCase {

	private ChangeManager changeManager;
	private List<Integer> callOrder;

	public void setUp() {
		changeManager = new ChangeManager();
		callOrder = new ArrayList<Integer>();
	}

	public void testOnMoveBall() {
		TestFieldListener fieldListener = new TestFieldListener();
		changeManager.addFieldListener(fieldListener);

		assertFalse(fieldListener.onMoveBallCall);
		assertNull(fieldListener.from);
		assertNull(fieldListener.to);
		assertTrue(changeManager.events.isEmpty());

		Cell from = new Cell();
		Cell to = new Cell();
		changeManager.onMoveBall(from, to);

		assertTrue(fieldListener.onMoveBallCall);
		assertSame(from, fieldListener.from);
		assertSame(to, fieldListener.to);
		assertTrue(changeManager.events.isEmpty());
	}

	public void testOnEraseCells() {
		TestFieldListener fieldListener1 = new TestFieldListener();
		changeManager.addFieldListener(fieldListener1);

		TestFieldListener fieldListener2 = new TestFieldListener();
		changeManager.addFieldListener(fieldListener2);

		TestGameModelListener gameModelListener = new TestGameModelListener();
		changeManager.addGameModelListener(gameModelListener);
		
		fieldListener1.gameModelListener = changeManager;

		assertTrue(callOrder.isEmpty());

		Collection<Cell> cells = new ArrayList<Cell>();
		changeManager.onEraseCells(cells);
		
		assertEquals(3, callOrder.size());
		assertEquals(1, (int) callOrder.get(0));
		assertEquals(1, (int) callOrder.get(1));
		assertEquals(2, (int) callOrder.get(2));
	}

	private class TestFieldListener implements FieldListener {

		boolean onMoveBallCall = false;
		Cell from, to;
		GameModelListener gameModelListener;

		public void onMoveBall(Cell from, Cell to) {
			onMoveBallCall = true;
			this.from = from;
			this.to = to;
		}

		public void onIllegalMoveBall(Cell from, Cell to) {
		}

		public void onFillCells(Collection<Cell> filledCells) {
		}

		public void onEraseCells(Collection<Cell> erasedCells) {
			callOrder.add(1);
			if (gameModelListener != null)
				gameModelListener.onLevelChanged();
		}
	}

	private class TestGameModelListener implements GameModelListener {

		public void onLevelChanged() {
			callOrder.add(2);
		}

		public void onGameOver() {
		}

		public void onGameComplete() {
		}
	}
}
