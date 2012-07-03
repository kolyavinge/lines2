package lines2.model;

import java.util.*;

public class ChangeManager implements FieldListener, GameModelListener {

	private interface Event {
		void execute();
	}

	private class MoveBall implements Event {

		private Object args;

		public MoveBall(Object args) {
			this.args = args;
		}

		public void execute() {
			Cell[] array = (Cell[]) args;
			Cell from = array[0];
			Cell to = array[1];
			for (FieldListener listener : fieldListeners)
				listener.onMoveBall(from, to);
		}
	}

	private class IllegalMoveBall implements Event {

		private Object args;

		public IllegalMoveBall(Object args) {
			this.args = args;
		}

		public void execute() {
			Cell[] array = (Cell[]) args;
			Cell from = array[0];
			Cell to = array[1];
			for (FieldListener listener : fieldListeners)
				listener.onIllegalMoveBall(from, to);
		}
	}

	private class FillCells implements Event {

		private Object args;

		public FillCells(Object args) {
			this.args = args;
		}

		public void execute() {
			@SuppressWarnings("unchecked")
			Collection<Cell> cells = (Collection<Cell>) args;
			for (FieldListener listener : fieldListeners)
				listener.onFillCells(cells);
		}
	}

	private class EraseCells implements Event {

		private Object args;

		public EraseCells(Object args) {
			this.args = args;
		}

		public void execute() {
			@SuppressWarnings("unchecked")
			Collection<Cell> cells = (Collection<Cell>) args;
			for (FieldListener listener : fieldListeners)
				listener.onEraseCells(cells);
		}
	}

	private class LevelChanged implements Event {

		public void execute() {
			for (GameModelListener listener : gameModelListeners)
				listener.onLevelChanged();
		}
	}

	private class GameOver implements Event {

		public void execute() {
			for (GameModelListener listener : gameModelListeners)
				listener.onGameOver();
		}
	}

	private class GameComplete implements Event {

		public void execute() {
			for (GameModelListener listener : gameModelListeners)
				listener.onGameComplete();
		}
	}

	private boolean eventsIsProccessed;
	private Collection<FieldListener> fieldListeners;
	private Collection<GameModelListener> gameModelListeners;
	Queue<Event> events;

	public ChangeManager() {
		eventsIsProccessed = false;
		fieldListeners = new ArrayList<FieldListener>();
		gameModelListeners = new ArrayList<GameModelListener>();
		events = new LinkedList<Event>();
	}

	public void addFieldListener(FieldListener fieldListener) {
		fieldListeners.add(fieldListener);
	}

	public void addGameModelListener(GameModelListener gameModelListener) {
		gameModelListeners.add(gameModelListener);
	}

	private void proccessEvents() {
		eventsIsProccessed = true;

		while (events.isEmpty() == false)
			events.poll().execute();

		eventsIsProccessed = false;
	}

	private void addNewEvent(Event event) {
		events.add(event);
		if (eventsIsProccessed == false)
			proccessEvents();
	}

	/* ------------------------ GameModelListener ------------------------ */

	public void onLevelChanged() {
		addNewEvent(new LevelChanged());
	}

	public void onGameOver() {
		addNewEvent(new GameOver());
	}

	public void onGameComplete() {
		addNewEvent(new GameComplete());
	}

	/* ------------------------ FieldListener ------------------------ */

	public void onMoveBall(Cell from, Cell to) {
		addNewEvent(new MoveBall(new Cell[] { from, to }));
	}

	public void onIllegalMoveBall(Cell from, Cell to) {
		addNewEvent(new IllegalMoveBall(new Cell[] { from, to }));
	}

	public void onFillCells(Collection<Cell> filledCells) {
		addNewEvent(new FillCells(filledCells));
	}

	public void onEraseCells(Collection<Cell> erasedCells) {
		addNewEvent(new EraseCells(erasedCells));
	}
}
