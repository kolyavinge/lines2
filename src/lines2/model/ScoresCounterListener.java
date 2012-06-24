package lines2.model;

public interface ScoresCounterListener {

	void onScoreChanged(ScoresCounter scoresCounter);
	
	void onScoreComplete(ScoresCounter scoresCounter);
}
