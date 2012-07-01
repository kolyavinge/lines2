package lines2.app;

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
		gameModel.getScoresCounter().addListener(scoresCounterListener);

		setScoresInTitleBar();

		FieldPresenter fieldPresenter = new FieldPresenter(gameModel.getField());

		FieldView fieldView = new FieldView(fieldPresenter, new BallViewFactory(this), this);

		setContentView(fieldView);
	}

	private void setScoresInTitleBar() {
		int current = gameModel.getScoresCounter().getCurrentScores();
		int total = gameModel.getScoresCounter().getTotalLevelScores();
		String scoresString = String.format("Очки: %d / %d", current, total);
		setTitle(scoresString);
	}

	private final DefaultScoresCounterListener scoresCounterListener = new DefaultScoresCounterListener() {
		@Override
		public void onScoreChanged(ScoresCounter scoresCounter) {
			setScoresInTitleBar();
		}
	};
}
