package lines2.model;

import java.util.ArrayList;
import java.util.Collection;

public final class GameModel implements MoveBallAction {

	private Field field;
	private ScoresCounter scoresCounter;
	private GameState gameState;
	private Collection<GameModelListener> listeners;

	public GameModel() {
		listeners = new ArrayList<GameModelListener>();
		gameState = GameState.NORMAL;
		initField();
		initScoresCounter();
	}

	public void moveBall(Cell fromCell, Cell toCell) {
		if (gameState == GameState.NORMAL) {
			tryMoveBall(fromCell, toCell);
		} else if (gameState == GameState.GAME_OVER) {
			raiseOnGameOver();
		}
	}

	private void tryMoveBall(Cell fromCell, Cell toCell) {
		try {
			field.moveBall(fromCell, toCell);
		} catch (MoveBallException exp) {
			// ignore
		} catch (FieldOverflowException exp) {
			gameOver();
		}
	}

	private void gameOver() {
		gameState = GameState.GAME_OVER;
		raiseOnGameOver();
	}

	private void initScoresCounter() {
		ScoresCounter scoresCounter = new ScoresCounter();
		scoresCounter.setEraseBallScore(10);
		scoresCounter.setTotalLevelScores(2500);
		setScoresCounter(scoresCounter);
	}

	private void initField() {
		FieldLoader fieldLoader = new FieldLoader();
		Field field = fieldLoader.getField(7, 6);
		setField(field);
	}

	public void addListener(GameModelListener listener) {
		listeners.add(listener);
	}

	public void removeListener(GameModelListener listener) {
		listeners.remove(listener);
	}

	private void raiseOnGameOver() {
		for (GameModelListener listener : listeners)
			listener.onGameOver();
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

	public GameState getGameState() {
		return gameState;
	}

	/* ------------------- FieldListener ------------------- */

	private final DefaultFieldListener fieldListener = new DefaultFieldListener() {

		@Override
		public void onEraseCells(Collection<Cell> erasedCells) {
			scoresCounter.addByErasedBalls(erasedCells.size());
		}
	};

	/* ------------------- ScoresCounterListener ------------------- */

	private final DefaultScoresCounterListener scoresCounterListener = new DefaultScoresCounterListener() {

		@Override
		public void onScoreComplete(ScoresCounter scoresCounter) {
		}
	};
}
