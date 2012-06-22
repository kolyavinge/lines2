package lines2.model;

public class ScoresCounter {

	private int currentScores;
	private int totalLevelScores;
	private int eraseBallScore = 100;
	private ScoresCounterListener listener;

	public void addByErasedBalls(int ballsCount) {
		currentScores += ballsCount * eraseBallScore;
		raiseOnScoreComplete();
	}

	private void raiseOnScoreComplete() {
		if (scoreComplete() && listener != null)
			listener.onScoreComplete();
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

	public void setListener(ScoresCounterListener listener) {
		this.listener = listener;
	}
	
	private void validateByPositive(int value, String valueName) {
		if (value <= 0)
			throw new IllegalArgumentException(valueName + " must be greater that zero");
	}
}
