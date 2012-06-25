package lines2.app;

import java.util.Map;

import lines2.model.Ball;
import lines2.model.Cell;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class FieldView extends View {

	private static final Paint whitePaint;
	private int cellSize;
	private FieldPresenter presenter;
	private BallRenderer ballRenderer = new BallRenderer();

	static {
		whitePaint = new Paint();
		whitePaint.setColor(Color.WHITE);
	}

	public FieldView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FieldView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FieldView(Context context) {
		super(context);
	}

	public FieldPresenter getPresenter() {
		return presenter;
	}

	public void setPresenter(FieldPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		this.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		calculateCellSize(canvas);
		drawSelectedCell(canvas);
		drawGrid(canvas);
		drawBalls(canvas);
		drawNextBalls(canvas);
	}

	private void calculateCellSize(Canvas canvas) {
		if (cellSize > 0)
			return;

		Rect bounds = canvas.getClipBounds();

		int width = bounds.right - bounds.left;
		int height = bounds.bottom - bounds.top;

		int cellSizeHeight = height / presenter.getFieldRows() - 1;
		int cellSizeWidth = width / presenter.getFieldCols() - 1;

		if (cellSizeHeight * presenter.getFieldCols() < width)
			cellSize = cellSizeHeight;
		else
			cellSize = cellSizeWidth;

		measure(cellSize * presenter.getFieldCols(), cellSize * presenter.getFieldRows());
	}

	private void drawSelectedCell(Canvas canvas) {
		if (presenter.noSelectedCell())
			return;

		Rect rect = getRectForCell(presenter.getSelectedCell());
		Paint paint = new Paint();
		paint.setColor(Color.YELLOW);
		canvas.drawRect(rect, paint);
	}

	private Rect getRectForCell(Cell cell) {
		return new Rect(
				cell.getCol() * cellSize,
				cell.getRow() * cellSize,
				cell.getCol() * cellSize + cellSize,
				cell.getRow() * cellSize + cellSize);
	}

	private void drawGrid(Canvas canvas) {
		drawHorizontalLines(canvas);
		drawVerticalLines(canvas);
	}

	private void drawHorizontalLines(Canvas canvas) {
		for (int row = 0; row <= presenter.getFieldRows(); row++) {
			float x0 = 0;
			float y0 = row * cellSize;
			float x1 = presenter.getFieldCols() * cellSize;
			float y1 = y0;
			canvas.drawLine(x0, y0, x1, y1, whitePaint);
		}
	}

	private void drawVerticalLines(Canvas canvas) {
		for (int col = 0; col <= presenter.getFieldCols(); col++) {
			float x0 = col * cellSize;
			float y0 = 0;
			float x1 = x0;
			float y1 = presenter.getFieldRows() * cellSize;
			canvas.drawLine(x0, y0, x1, y1, whitePaint);
		}
	}

	private void drawBalls(Canvas canvas) {
		ballRenderer.setCellSize(cellSize);
		ballRenderer.setNextBall(false);
		ballRenderer.setCanvas(canvas);
		for (Cell cell : presenter.getFieldCells()) {
			if (cell.isEmpty() == false) {
				canvas.save();
				canvas.translate(cellSize * cell.getCol(), cellSize * cell.getRow());
				ballRenderer.drawBall(cell.getBall());
				canvas.restore();
			}
		}
	}

	private void drawNextBalls(Canvas canvas) {
		ballRenderer.setCellSize(cellSize);
		ballRenderer.setNextBall(true);
		ballRenderer.setCanvas(canvas);
		for (Map.Entry<Cell, Ball> kv : presenter.getNextFillCells().entrySet()) {
			Cell cell = kv.getKey();
			Ball ball = kv.getValue();
			canvas.save();
			canvas.translate(cellSize * cell.getCol(), cellSize * cell.getRow());
			ballRenderer.drawBall(ball);
			canvas.restore();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int row = (int) (event.getY() / cellSize);
		int col = (int) (event.getX() / cellSize);
		presenter.selectCell(row, col);
		postInvalidate();

		return super.onTouchEvent(event);
	}
}
