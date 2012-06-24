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

		FieldPresenter fieldPresenter = new FieldPresenter(gameModel.getField());

		FieldView fieldView = new FieldView(this);
		fieldView.setPresenter(fieldPresenter);

		MainView mainView = new MainView(this);
		mainView.setFieldView(fieldView);

		setContentView(mainView);

		setScoresInTitleBar(0);
	}

	private void setScoresInTitleBar(int scores) {
		String scoresString = Integer.toString(scores);
		Lines2Activity.this.setTitle("Очки: " + scoresString);
	}

	private final DefaultScoresCounterListener scoresCounterListener = new DefaultScoresCounterListener() {
		@Override
		public void onScoreChanged(ScoresCounter scoresCounter) {
			setScoresInTitleBar(scoresCounter.getCurrentScores());
		}
	};
}
