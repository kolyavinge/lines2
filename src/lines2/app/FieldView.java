package lines2.app;

import java.util.Map;

import lines2.model.Ball;
import lines2.model.Cell;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class FieldView extends View {

	private float cellSize;
	private FieldPresenter presenter;

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

		float width = bounds.right - bounds.left;
		float height = bounds.bottom - bounds.top;

		float cellSizeHeight = height / presenter.getFieldRows() - 0.1f;
		float cellSizeWidth = width / presenter.getFieldCols() - 0.1f;

		if (cellSizeHeight * presenter.getFieldCols() < width)
			cellSize = cellSizeHeight;
		else
			cellSize = cellSizeWidth;

		measure((int)cellSize * presenter.getFieldCols(), (int)cellSize * presenter.getFieldRows());
	}

	private void drawSelectedCell(Canvas canvas) {
		if (presenter.noSelectedCell())
			return;

		RectF rect = getRectForCell(presenter.getSelectedCell());
		Paint paint = new Paint();
		paint.setColor(Color.YELLOW);
		canvas.drawRect(rect, paint);
	}

	private RectF getRectForCell(Cell cell) {
		return new RectF(
				cell.getCol() * cellSize,
				cell.getRow() * cellSize,
				cell.getCol() * cellSize + cellSize,
				cell.getRow() * cellSize + cellSize);
	}

	private void drawGrid(Canvas canvas) {
		GridRenderer gridRenderer = new GridRenderer();
		gridRenderer.setCanvas(canvas);
		gridRenderer.setCellSize(cellSize);
		gridRenderer.setFieldSize(presenter.getFieldRows(), presenter.getFieldCols());
		gridRenderer.drawGrid();
	}

	private void drawBalls(Canvas canvas) {
		BallRenderer ballRenderer = new BallRenderer();
		ballRenderer.setCellSize(cellSize);
		ballRenderer.setNextBall(false);
		ballRenderer.setCanvas(canvas);
		for (Cell cell : presenter.getFieldCells()) {
			if (cell.isEmpty() == false) {
				ballRenderer.setBallPosition(cellSize * cell.getCol(), cellSize * cell.getRow());
				ballRenderer.drawBall(cell.getBall());
			}
		}
	}

	private void drawNextBalls(Canvas canvas) {
		BallRenderer ballRenderer = new BallRenderer();
		ballRenderer.setCellSize(cellSize);
		ballRenderer.setNextBall(true);
		ballRenderer.setCanvas(canvas);
		for (Map.Entry<Cell, Ball> kv : presenter.getNextFillCells().entrySet()) {
			Cell cell = kv.getKey();
			Ball ball = kv.getValue();
			ballRenderer.setBallPosition(cellSize * cell.getCol(), cellSize * cell.getRow());
			ballRenderer.drawBall(ball);
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
