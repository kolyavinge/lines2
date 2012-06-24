package lines2.model;

import junit.framework.TestCase;

public class ScoresCounterTest extends TestCase {

	private ScoresCounter scoreCounter;

	public void setUp() {
		scoreCounter = new ScoresCounter();
	}

	public void testConstructor() {
		assertEquals(0, scoreCounter.getCurrentScores());
		assertEquals(0, scoreCounter.getTotalLevelScores());
	}

	public void testByPositiveValues() {
		try {
			scoreCounter.setCurrentScores(-1);
			fail();
		} catch (IllegalArgumentException exp) {
		}

		try {
			scoreCounter.setEraseBallScore(-1);
			fail();
		} catch (IllegalArgumentException exp) {
		}

		try {
			scoreCounter.setTotalLevelScores(-1);
			fail();
		} catch (IllegalArgumentException exp) {
		}
	}

	public void testRaiseOnScoreComplete() {
		TestScoreCounterListener listener = new TestScoreCounterListener();
		scoreCounter.addListener(listener);
		scoreCounter.setEraseBallScore(1);
		scoreCounter.setTotalLevelScores(5);

		scoreCounter.addByErasedBalls(1);
		assertFalse(listener.isOnScoreCompleteCall());

		scoreCounter.addByErasedBalls(3);
		assertFalse(listener.isOnScoreCompleteCall());

		scoreCounter.addByErasedBalls(1);
		assertTrue(listener.isOnScoreCompleteCall());
	}

	private static class TestScoreCounterListener implements ScoresCounterListener {

		private boolean onScoreCompleteCall = false;

		public void onScoreComplete(ScoresCounter scoresCounter) {
			onScoreCompleteCall = true;
		}

		public void onScoreChanged(ScoresCounter scoresCounter) {
		}

		public boolean isOnScoreCompleteCall() {
			return onScoreCompleteCall;
		}
	}
}
