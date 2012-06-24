package lines2.model;

import junit.framework.TestCase;
import lines2.common.TestUtils;

public class GameModelTest extends TestCase {

	private GameModel gameModel;
	
	public void setUp() {
		gameModel = new GameModel();
	}
	
	public void testChangeScores() {
		Field field = new Field(10, 10);
		field.setMoveStrategy(TestUtils.getMoveStrategyStub());
		field.setEraseStrategy(new StraightEraseStrategy(5));
		field.setFillStrategy(TestUtils.getFillStrategyStub());
		field.getCell(0, 0).setBall(TestUtils.getColoredBall(Color.RED));
		field.getCell(1, 0).setBall(TestUtils.getColoredBall(Color.RED));
		field.getCell(2, 0).setBall(TestUtils.getColoredBall(Color.RED));
		field.getCell(3, 0).setBall(TestUtils.getColoredBall(Color.RED));
		field.getCell(5, 0).setBall(TestUtils.getColoredBall(Color.RED));
		gameModel.setField(field);
		gameModel.getScoresCounter().setEraseBallScore(100);
		assertEquals(0, gameModel.getScoresCounter().getCurrentScores());
		field.moveBall(field.getCell(5, 0), field.getCell(4, 0));
		assertEquals(500, gameModel.getScoresCounter().getCurrentScores());
	}
}
