package lines2.app;

import java.util.HashMap;
import java.util.Map;

import lines2.model.Ball;
import lines2.model.Cell;
import lines2.model.ColoredBall;
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

	//	private static final int offsetX = 10, offsetY = 20;
	private static final Map<lines2.model.Color, Integer> ballColors;
	private int cellSize = 40;
	private FieldPresenter presenter;

	static {
		ballColors = new HashMap<lines2.model.Color, Integer>();
		ballColors.put(lines2.model.Color.RED, android.graphics.Color.RED);
		ballColors.put(lines2.model.Color.GREEN, android.graphics.Color.GREEN);
		ballColors.put(lines2.model.Color.BLUE, android.graphics.Color.BLUE);
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

	public int getCellSize() {
		return cellSize;
	}

	public void setCellSize(int cellSize) {
		this.cellSize = cellSize;
	}

	public FieldPresenter getPresenter() {
		return presenter;
	}

	public void setPresenter(FieldPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.save();
		//		canvas.translate(offsetX, offsetY);

		drawSelectedCell(canvas);
		drawGrid(canvas);
		drawCellBalls(canvas);

		canvas.restore();
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
		Paint paint = getWhitePaint();
		for (int row = 0; row <= presenter.getFieldRows(); row++) {
			float x0 = 0;
			float y0 = row * cellSize;
			float x1 = presenter.getFieldCols() * cellSize;
			float y1 = y0;
			canvas.drawLine(x0, y0, x1, y1, paint);
		}
	}

	private void drawVerticalLines(Canvas canvas) {
		Paint paint = getWhitePaint();
		for (int col = 0; col <= presenter.getFieldCols(); col++) {
			float x0 = col * cellSize;
			float y0 = 0;
			float x1 = x0;
			float y1 = presenter.getFieldRows() * cellSize;
			canvas.drawLine(x0, y0, x1, y1, paint);
		}
	}

	private Paint getWhitePaint() {
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);

		return paint;
	}

	private void drawCellBalls(Canvas canvas) {
		for (Cell cell : presenter.getFieldCells()) {
			drawCellBall(canvas, cell);
		}
	}

	private void drawCellBall(Canvas canvas, Cell cell) {
		if (cell.isEmpty())
			return;

		canvas.save();
		canvas.translate(cellSize * cell.getCol(), cellSize * cell.getRow());
		drawBall(canvas, cell.getBall());
		canvas.restore();
	}

	private void drawBall(Canvas canvas, Ball ball) {
		switch (ball.getType()) {
		case COLORED_BALL:
			drawColoredBall(canvas, (ColoredBall) ball);
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	private void drawColoredBall(Canvas canvas, ColoredBall ball) {
		RectF rect = new RectF(0, 0, cellSize, cellSize);
		Paint paint = new Paint();
		paint.setColor(getBallColor(ball.getColor()));
		canvas.drawOval(rect, paint);
	}

	private int getBallColor(lines2.model.Color color) {
		if (ballColors.containsKey(color) == false)
			throw new IllegalArgumentException();

		return ballColors.get(color);
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
