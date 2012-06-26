package lines2.app;

import java.util.HashMap;
import java.util.Map;

import lines2.model.Ball;
import lines2.model.ColoredBall;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

class BallRenderer {

	private static final Map<lines2.model.Color, Integer> ballColors;
	private static final int ballOffset = 2;
	private static final int nextBallAlpha = 150;

	private Canvas canvas;
	private boolean isNextBall;
	private int cellSize;
	private int ballX, ballY;

	static {
		ballColors = new HashMap<lines2.model.Color, Integer>();
		ballColors.put(lines2.model.Color.RED, android.graphics.Color.RED);
		ballColors.put(lines2.model.Color.GREEN, android.graphics.Color.GREEN);
		ballColors.put(lines2.model.Color.BLUE, android.graphics.Color.BLUE);
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public void setNextBall(boolean isNextBall) {
		this.isNextBall = isNextBall;
	}

	public void setCellSize(int cellSize) {
		this.cellSize = cellSize;
	}

	public void setBallPosition(int x, int y) {
		ballX = x;
		ballY = y;
	}

	public void drawBall(Ball ball) {
		canvas.save();
		canvas.translate(ballX, ballY);
		switch (ball.getType()) {
		case COLORED_BALL:
			drawColoredBall((ColoredBall) ball);
			break;
		default:
			throw new IllegalArgumentException();
		}
		canvas.restore();
	}

	public void drawColoredBall(ColoredBall ball) {
		RectF rect = isNextBall ? getRectForNextBall(ball) : getRectForBall(ball);
		Paint paint = isNextBall ? getPaintForNextColoredBall(ball) : getPaintForColoredBall(ball);
		canvas.drawOval(rect, paint);
	}

	private Paint getPaintForColoredBall(ColoredBall ball) {
		Paint paint = getPaintForBall();
		paint.setColor(getBallColor(ball.getColor()));

		return paint;
	}

	private Paint getPaintForNextColoredBall(ColoredBall ball) {
		Paint paint = getPaintForColoredBall(ball);
		paint.setAlpha(nextBallAlpha);

		return paint;
	}

	private Paint getPaintForBall() {
		Paint paint = new Paint();
		paint.setAntiAlias(true);

		return paint;
	}

	private RectF getRectForBall(Ball ball) {
		return new RectF(ballOffset, ballOffset, cellSize - ballOffset, cellSize - ballOffset);
	}

	private RectF getRectForNextBall(Ball ball) {
		final float z = 6.0f;
		return new RectF(z * ballOffset, z * ballOffset, cellSize - z * ballOffset, cellSize - z * ballOffset);
	}

	private int getBallColor(lines2.model.Color color) {
		if (ballColors.containsKey(color) == false)
			throw new IllegalArgumentException();

		return ballColors.get(color);
	}
}
