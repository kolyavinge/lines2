package lines2.model;

import java.util.Collection;

public final class GameModel extends GameModelListenerManager implements MoveBallAction {

	private int levelNumber;
	private Field field;
	private ScoresCounter scoresCounter;
	private GameState gameState;
	private LevelIterator levelIterator;

	public GameModel() {
		gameState = GameState.NORMAL;
		levelIterator = new LevelIterator();
		Level level = levelIterator.next();
		initLevel(level);
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

	private void initLevel(Level level) {
		levelNumber = level.getNumber();
		initField(level);
		initScoresCounter(level);
	}

	private void initScoresCounter(Level level) {
		ScoresCounter scoresCounter = new ScoresCounter();
		scoresCounter.setEraseBallScore(10);
		scoresCounter.setTotalLevelScores(level.getTotalLevelScores());
		setScoresCounter(scoresCounter);
	}

	private void initField(Level level) {
		Field field = new Field(level.getFieldRows(), level.getFieldCols());
		field.setEraseStrategy(level.getEraseStrategy());
		field.setFillStrategy(level.getFillStrategy());
		field.setMoveStrategy(new SimpleMoveStrategy());
		field.populate();
		setField(field);
	}

	public int getLevelNumber() {
		return levelNumber;
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

	private void gameOver() {
		gameState = GameState.GAME_OVER;
		raiseOnGameOver();
	}

	private void gameComplete() {
		gameState = GameState.GAME_COMPLETE;
		raiseOnGameComplete();
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
			if (levelIterator.hasNext()) {
				Level level = levelIterator.next();
				initLevel(level);
				raiseOnLevelChanged();
			} else {
				gameComplete();
			}
		}
	};
}
