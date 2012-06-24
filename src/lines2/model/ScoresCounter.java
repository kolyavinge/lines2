package lines2.model;

import java.util.ArrayList;
import java.util.List;

public class ScoresCounter {

	private int currentScores;
	private int totalLevelScores;
	private int eraseBallScore = 100;
	private List<ScoresCounterListener> listeners;

	public ScoresCounter() {
		listeners = new ArrayList<ScoresCounterListener>();
	}

	public void addByErasedBalls(int ballsCount) {
		currentScores += ballsCount * eraseBallScore;
		raiseOnScoreChanged();
		if (scoreComplete())
			raiseOnScoreComplete();
	}

	private void raiseOnScoreComplete() {
		for (ScoresCounterListener listener : listeners)
			listener.onScoreComplete(this);
	}

	private void raiseOnScoreChanged() {
		for (ScoresCounterListener listener : listeners)
			listener.onScoreChanged(this);
	}

	private boolean scoreComplete() {
		return currentScores >= totalLevelScores;
	}

	public int getCurrentScores() {
		return currentScores;
	}

	public void setCurrentScores(int currentScores) {
		validateByPositive(currentScores, "currentScores");
		this.currentScores = currentScores;
	}

	public int getTotalLevelScores() {
		return totalLevelScores;
	}

	public void setTotalLevelScores(int totalLevelScores) {
		validateByPositive(totalLevelScores, "totalLevelScores");
		this.totalLevelScores = totalLevelScores;
	}

	public int getEraseBallScore() {
		return eraseBallScore;
	}

	public void setEraseBallScore(int eraseBallScore) {
		validateByPositive(eraseBallScore, "eraseBallScore");
		this.eraseBallScore = eraseBallScore;
	}

	public void addListener(ScoresCounterListener listener) {
		this.listeners.add(listener);
	}

	public void removeListener(ScoresCounterListener listener) {
		this.listeners.remove(listener);
	}

	private void validateByPositive(int value, String valueName) {
		if (value <= 0)
			throw new IllegalArgumentException(valueName + " must be greater that zero");
	}
}
