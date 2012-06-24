package lines2.model;

import java.util.Collection;

public final class GameModel {

	private Field field;
	private ScoresCounter scoresCounter;
	private GameModelListener listener;

	public GameModel() {
		initField();
		initScoresCounter();
	}

	private void initScoresCounter() {
		ScoresCounter scoresCounter = new ScoresCounter();
		scoresCounter.setTotalLevelScores(25000);
		setScoresCounter(scoresCounter);
	}

	private void initField() {
		FieldLoader fieldLoader = new FieldLoader();
		Field field = fieldLoader.getField(7, 6);
		setField(field);
	}

	public void setListener(GameModelListener listener) {
		this.listener = listener;
	}

	public Field getField() {
		return field;
	}

	void setField(Field field) {
		this.field = field;
		this.field.addFieldListener(fieldListener);
	}

	public ScoresCounter getScoresCounter() {
		return scoresCounter;
	}

	void setScoresCounter(ScoresCounter scoresCounter) {
		this.scoresCounter = scoresCounter;
		this.scoresCounter.addListener(scoresCounterListener);
	}

	private final FieldListener fieldListener = new FieldListener() {

		public void onEraseCells(Collection<Cell> erasedCells) {
			scoresCounter.addByErasedBalls(erasedCells.size());
		}
	};

	private final ScoresCounterListener scoresCounterListener = new ScoresCounterListener() {

		public void onScoreComplete(ScoresCounter scoresCounter) {
		}

		public void onScoreChanged(ScoresCounter scoresCounter) {
		}
	};
}
