package lines2.model;

public final class GameModel implements ScoresCounterListener {

	private Field field;
	private ScoresCounter scoresCounter;

	public GameModel() {
		FieldLoader fieldLoader = new FieldLoader();
		field = fieldLoader.getField(10, 8);
		scoresCounter = new ScoresCounter();
		scoresCounter.setListener(this);
	}

	public void onScoreComplete() {
	}

	public Field getField() {
		return field;
	}
}
