package lines2.common;

import java.util.Collection;
import java.util.Collections;

import lines2.model.Ball;
import lines2.model.BallType;
import lines2.model.Cell;
import lines2.model.Color;
import lines2.model.ColoredBall;
import lines2.model.EraseStrategy;
import lines2.model.Field;
import lines2.model.FillStrategy;
import lines2.model.MoveStrategy;

public final class TestUtils {

	private static class BallStub extends Ball {
		public BallStub() {
			super(BallType._UNKNOWN);
		}
	}

	public static Ball getBall() {
		return new BallStub();
	}

	public static ColoredBall getColoredBall(Color color) {
		return new ColoredBall(color);
	}

	public static MoveStrategy getMoveStrategyStub() {
		return new MoveStrategy() {
			public boolean checkMove(Field field, Cell startCell, Cell finishCell) {
				return true;
			}
		};
	}

	public static EraseStrategy getEraseStrategyStub() {
		return new EraseStrategy() {
			public Collection<Cell> getErasedCells(Field field, Cell lastStepCell) {
				return Collections.emptyList();
			}
		};
	}

	public static FillStrategy getFillStrategyStub() {
		return new FillStrategy() {
			public Collection<Ball> getNextBalls(Iterable<Cell> cells) {
				return Collections.emptyList();
			}
		};
	}
}
