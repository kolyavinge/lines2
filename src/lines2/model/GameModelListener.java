package lines2.model;

public interface GameModelListener {
	
	void onLevelChanged();

	void onGameOver();
	
	void onGameComplete();
}
