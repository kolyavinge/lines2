package lines2.common;

import lines2.model.Ball;
import lines2.model.BallType;

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
}
