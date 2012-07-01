package lines2.app;

import java.util.*;
import lines2.model.*;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import static lines2.model.Color.*;

public class BallViewFactory {

	private Context context;
	private Map<lines2.model.Color, Bitmap> bitmaps;

	public BallViewFactory(Context context) {
		this.context = context;
		// сразу загрузим все картинки иначе будут тормоза
		loadBitmaps(context);
	}

	private void loadBitmaps(Context context) {
		bitmaps = new HashMap<lines2.model.Color, Bitmap>();
		bitmaps.put(RED, BitmapFactory.decodeResource(context.getResources(), R.drawable.ball_red));
		bitmaps.put(GREEN, BitmapFactory.decodeResource(context.getResources(), R.drawable.ball_green));
		bitmaps.put(BLUE, BitmapFactory.decodeResource(context.getResources(), R.drawable.ball_blue));
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

		throw new IllegalArgumentException();
	}

	private Bitmap getColoredBallBitmap(ColoredBall ball) {
		if (bitmaps.containsKey(ball.getColor()))
			return bitmaps.get(ball.getColor());
		else
			throw new IllegalArgumentException();
	}
}
