package lines2.app;

import lines2.model.DefaultGameModelListener;
import lines2.model.DefaultScoresCounterListener;
import lines2.model.GameModel;
import lines2.model.ScoresCounter;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class Lines2Activity extends Activity {

	private GameModel gameModel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		gameModel = new GameModel();
		gameModel.addListener(gameModelListener);
		initLevel();
	}

	private void initLevel() {
		initScoresCounter();
		initFieldView();
	}

	private void initScoresCounter() {
		gameModel.getScoresCounter().addListener(scoresCounterListener);
		setTextInTitleBar();
	}

	private void initFieldView() {
		FieldPresenter fieldPresenter = new FieldPresenter(gameModel.getField(), gameModel);
		BallViewFactory ballViewFactory = new BallViewFactory(this);
		FieldView fieldView = new FieldView(fieldPresenter, ballViewFactory, this);
		setContentView(fieldView);
	}

	private void setTextInTitleBar() {
		ScoresCounter scoresCounter = gameModel.getScoresCounter();
		int current = scoresCounter.getCurrentScores();
		int total = scoresCounter.getTotalLevelScores();
		int levelNumber = gameModel.getLevelNumber() + 1;
		String scoresString = String.format("Уровень: %d | Очки: %d / %d", levelNumber, current, total);
		setTitle(scoresString);
	}

	/* -------------------------- GameModelListener -------------------------- */

	private final DefaultGameModelListener gameModelListener = new DefaultGameModelListener() {

		@Override
		public void onLevelChanged() {
			initLevel();
		}

		@Override
		public void onGameOver() {
			GameOverAlertDialog alertDialog = new GameOverAlertDialog(Lines2Activity.this);
			alertDialog.show();
		}
	};

	/* -------------------------- ScoresCounterListener -------------------------- */

	private final DefaultScoresCounterListener scoresCounterListener = new DefaultScoresCounterListener() {

		@Override
		public void onScoreChanged(ScoresCounter scoresCounter) {
			setTextInTitleBar();
		}
	};
}
