package lines2.app;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MainView extends View {

	private View fieldView;

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
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.save();
		canvas.translate(0, 4);
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
