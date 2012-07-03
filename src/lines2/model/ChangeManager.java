package lines2.model;

import java.util.*;

public class ChangeManager implements FieldListener, GameModelListener {

	private static enum EventName {
		MoveBall,
		IllegalMoveBall,
		FillCells,
		EraseCells,
		LevelChanged,
		GameOver,
		GameComplete,
	}

	private static class EventInfo {
		public EventName name;
		public Object args;
	}

	private boolean eventsIsProccessed = false;
	private Collection<FieldListener> fieldListeners;
	private Collection<GameModelListener> gameModelListeners;
	Queue<EventInfo> events;

	public ChangeManager() {
		fieldListeners = new ArrayList<FieldListener>();
		gameModelListeners = new ArrayList<GameModelListener>();
		events = new LinkedList<EventInfo>();
	}

	public void addFieldListener(FieldListener fieldListener) {
		fieldListeners.add(fieldListener);
	}

	public void addGameModelListener(GameModelListener gameModelListener) {
		gameModelListeners.add(gameModelListener);
	}

	private void proccessEvents() {
		eventsIsProccessed = true;

		while (events.isEmpty() == false) {
			EventInfo eventInfo = events.poll();
			switch (eventInfo.name) {
			case MoveBall:
				moveBallProccess(eventInfo);
				break;
			case IllegalMoveBall:
				illegalMoveBallProccess(eventInfo);
				break;
			case FillCells:
				fillCellsProccess(eventInfo);
				break;
			case EraseCells:
				eraseCellsProccess(eventInfo);
				break;
			case LevelChanged:
				levelChangeProccess();
				break;
			case GameOver:
				gameOverProccess();
				break;
			case GameComplete:
				gameCompleteProccess();
				break;
			default:
				throw new IllegalArgumentException();
			}
		}

		eventsIsProccessed = false;
	}

	private void levelChangeProccess() {
		for (GameModelListener listener : gameModelListeners)
			listener.onLevelChanged();
	}

	private void gameOverProccess() {
		for (GameModelListener listener : gameModelListeners)
			listener.onGameOver();
	}

	private void gameCompleteProccess() {
		for (GameModelListener listener : gameModelListeners)
			listener.onGameComplete();
	}

	private void eraseCellsProccess(EventInfo eventInfo) {
		@SuppressWarnings("unchecked")
		Collection<Cell> cells = (Collection<Cell>) eventInfo.args;
		for (FieldListener listener : fieldListeners)
			listener.onEraseCells(cells);
	}

	private void illegalMoveBallProccess(EventInfo eventInfo) {
		Cell[] array = (Cell[]) eventInfo.args;
		Cell from = array[0];
		Cell to = array[1];
		for (FieldListener listener : fieldListeners)
			listener.onIllegalMoveBall(from, to);
	}

	private void fillCellsProccess(EventInfo eventInfo) {
		@SuppressWarnings("unchecked")
		Collection<Cell> cells = (Collection<Cell>) eventInfo.args;
		for (FieldListener listener : fieldListeners)
			listener.onFillCells(cells);
	}

	private void moveBallProccess(EventInfo eventInfo) {
		Cell[] array = (Cell[]) eventInfo.args;
		Cell from = array[0];
		Cell to = array[1];
		for (FieldListener listener : fieldListeners)
			listener.onMoveBall(from, to);
	}

	private void addNewEvent(EventName name, Object args) {
		EventInfo eventInfo = new EventInfo();
		eventInfo.name = name;
		eventInfo.args = args;
		events.add(eventInfo);
		if (eventsIsProccessed == false)
			proccessEvents();
	}

	/* GameModelListener */

	public void onLevelChanged() {
		addNewEvent(EventName.LevelChanged, null);
	}

	public void onGameOver() {
		addNewEvent(EventName.GameOver, null);
	}

	public void onGameComplete() {
		addNewEvent(EventName.GameComplete, null);
	}

	/* FieldListener */

	public void onMoveBall(Cell from, Cell to) {
		addNewEvent(EventName.MoveBall, new Cell[] { from, to });
	}

	public void onIllegalMoveBall(Cell from, Cell to) {
		addNewEvent(EventName.IllegalMoveBall, new Cell[] { from, to });
	}

	public void onFillCells(Collection<Cell> filledCells) {
		addNewEvent(EventName.FillCells, filledCells);
	}

	public void onEraseCells(Collection<Cell> erasedCells) {
		addNewEvent(EventName.EraseCells, erasedCells);
	}
}
