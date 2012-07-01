package lines2.app;

import static android.graphics.BitmapFactory.decodeResource;
import static lines2.app.R.drawable.ball_blue;
import static lines2.app.R.drawable.ball_green;
import static lines2.app.R.drawable.ball_orange;
import static lines2.app.R.drawable.ball_purple;
import static lines2.app.R.drawable.ball_red;
import static lines2.model.Color.BLUE;
import static lines2.model.Color.GREEN;
import static lines2.model.Color.ORANGE;
import static lines2.model.Color.PURPLE;
import static lines2.model.Color.RED;
import lines2.model.Ball;
import lines2.model.Color;
import lines2.model.ColoredBall;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;

class BallViewFactory {

	private Context context;
	private Bitmap[] bitmaps;

	public BallViewFactory(Context context) {
		this.context = context;
		loadBitmaps(context); // сразу загрузим все картинки иначе будут тормоза
	}

	private void loadBitmaps(Context context) {
		Resources res = context.getResources();
		bitmaps = new Bitmap[Color.getColorsCount()];
		bitmaps[RED.getNumber()] = decodeResource(res, ball_red);
		bitmaps[GREEN.getNumber()] = decodeResource(res, ball_green);
		bitmaps[BLUE.getNumber()] = decodeResource(res, ball_blue);
		bitmaps[ORANGE.getNumber()] = decodeResource(res, ball_orange);
		bitmaps[PURPLE.getNumber()] = decodeResource(res, ball_purple);
	}

	public BallView getBallView(Ball ball) {
		Bitmap ballBitmap = getBallBitmap(ball);
		BallView ballView = new BallView(ball, context);
		ballView.setBallBitmap(ballBitmap);
		ballView.setNextBall(false);

		return ballView;
	}

	public BallView getNextBallView(Ball ball) {
		BallView ballView = getBallView(ball);
		ballView.setNextBall(true);

		return ballView;
	}

	private Bitmap getBallBitmap(Ball ball) {
		switch (ball.getType()) {
		case COLORED_BALL:
			return getColoredBallBitmap((ColoredBall) ball);
		}

		throw new IllegalArgumentException("Wrong ball type");
	}

	private Bitmap getColoredBallBitmap(ColoredBall ball) {
		try {
			return bitmaps[ball.getColor().getNumber()];
		} catch (Exception exp) {
			throw new IllegalArgumentException("Wrong ball color");
		}
	}
}
