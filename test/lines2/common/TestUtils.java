package lines2.common;

import lines2.model.Ball;
import lines2.model.BallType;
import lines2.model.Color;
import lines2.model.ColoredBall;

public final class TestUtils {

	public static Ball getBall() {
		return new Ball() {

			//			public Cell getCell() {
			//				return null;
			//			}

			public BallType getType() {
				return null;
			}
		};
	}
	
	public static ColoredBall getColoredBall(Color color) {
		return new ColoredBall(color);
	}
}
