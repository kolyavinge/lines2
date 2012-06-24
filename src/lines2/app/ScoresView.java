package lines2.app;

import lines2.model.ScoresCounter;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ScoresView extends View {

	private ScoresCounter scoresCounter;
	private Paint paint;

	public ScoresView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initSize();
		paintInit();
	}

	public ScoresView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initSize();
		paintInit();
	}

	public ScoresView(Context context) {
		super(context);
		initSize();
		paintInit();
	}

	private void initSize() {
		measure(400, 20);
	}

	private void paintInit() {
		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setTextSize(25);
	}

	public ScoresCounter getScoresCounter() {
		return scoresCounter;
	}

	public void setScoresCounter(ScoresCounter scoresCounter) {
		this.scoresCounter = scoresCounter;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		String scoresString = Integer.toString(scoresCounter.getCurrentScores());
		canvas.drawText(scoresString, 0, 0, paint);
	}
}