package lines2.app;

import lines2.model.GameModel;
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

		FieldPresenter fieldPresenter = new FieldPresenter(gameModel.getField());

		FieldView fieldView = new FieldView(this);
		fieldView.setPresenter(fieldPresenter);
		
		ScoresView scoresView = new ScoresView(this);
		scoresView.setScoresCounter(gameModel.getScoresCounter());

		MainView mainView = new MainView(this);
		mainView.setFieldView(fieldView);
		mainView.setScoresView(scoresView);

		setContentView(mainView);
	}
}
