package lines2.app;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MainView extends View {

	private static final int offsetX = 10, offsetY = 10;

	private View fieldView;
	private View scoresView;

	public MainView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MainView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MainView(Context context) {
		super(context);
	}

	public void setFieldView(View fieldView) {
		this.fieldView = fieldView;
//		ArrayList<View> array = new ArrayList<View>();
//		array.add(fieldView);
//		this.addTouchables(array);
	}

	public void setScoresView(View scoresView) {
		this.scoresView = scoresView;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.save();
		canvas.translate(offsetX, offsetY);

		canvas.save();
		canvas.translate(0, 20);
		scoresView.draw(canvas);
		canvas.restore();

		canvas.translate(0, 25);
		fieldView.draw(canvas);

		canvas.restore();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		fieldView.onTouchEvent(event);
		this.postInvalidate();

		return super.onTouchEvent(event);
	}
}
