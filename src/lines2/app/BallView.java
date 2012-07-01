package lines2.app;

import lines2.model.Ball;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class BallView extends View {

	private static final int nextBallAlpha = 120;
	private static final float nextBallScale = 0.1f;

	private boolean isNextBall;
	private float width, height;
	private Bitmap ballBitmap;
	private Paint paint;
	private Ball ball;

	public BallView(Ball ball, Context context) {
		super(context);
		this.ball = ball;
		paint = new Paint(Paint.FILTER_BITMAP_FLAG);
	}

	public Ball getBall() {
		return ball;
	}

	public boolean isNextBall() {
		return isNextBall;
	}

	public void setNextBall(boolean isNextBall) {
		this.isNextBall = isNextBall;
	}

	public Bitmap getBallBitmap() {
		return ballBitmap;
	}

	public void setBallBitmap(Bitmap ballBitmap) {
		this.ballBitmap = ballBitmap;
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		width = right - left;
		height = bottom - top;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		paint.setAlpha(isNextBall ? nextBallAlpha : 255);
		RectF rect = isNextBall ? getNextBallRect() : getBallRect();
		canvas.drawBitmap(ballBitmap, null, rect, paint);
	}

	private RectF getBallRect() {
		return new RectF(0, 0, width, height);
	}

	private RectF getNextBallRect() {
		float widthScale = nextBallScale * width;
		float heightScale = nextBallScale * height;

		return new RectF(widthScale, heightScale, width - widthScale, height - heightScale);
	}
}
