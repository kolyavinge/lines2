package lines2.app;

import java.util.HashMap;
import java.util.Map;

import lines2.model.Ball;
import lines2.model.Cell;
import lines2.model.ColoredBall;
import lines2.model.Field;
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

	private static final int offsetX = 10, offsetY = 20;
	private static final Map<lines2.model.Color, Integer> ballColors;
	private int cellSize = 40;
	private Field field;
	private Cell selectedCell;

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

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (field == null)
			return;

		canvas.save();
//		canvas.translate(offsetX, offsetY);

		drawSelectedCell(canvas);
		drawGrid(canvas);
		drawCellBalls(canvas);

		canvas.restore();
	}

	private void drawSelectedCell(Canvas canvas) {
		if (noSelectedCell())
			return;

		Rect rect = new Rect(
				selectedCell.getCol() * cellSize,
				selectedCell.getRow() * cellSize,
				selectedCell.getCol() * cellSize + cellSize,
				selectedCell.getRow() * cellSize + cellSize);
		Paint paint = new Paint();
		paint.setColor(Color.YELLOW);
		canvas.drawRect(rect, paint);
	}

	private void drawGrid(Canvas canvas) {
		drawHorizontalLines(canvas);
		drawVerticalLines(canvas);
	}

	private void drawHorizontalLines(Canvas canvas) {
		Paint paint = getWhitePaint();
		for (int row = 0; row <= field.getRows(); row++) {
			float x0 = 0;
			float y0 = row * cellSize;
			float x1 = field.getCols() * cellSize;
			float y1 = y0;
			canvas.drawLine(x0, y0, x1, y1, paint);
		}
	}

	private void drawVerticalLines(Canvas canvas) {
		Paint paint = getWhitePaint();
		for (int col = 0; col <= field.getCols(); col++) {
			float x0 = col * cellSize;
			float y0 = 0;
			float x1 = x0;
			float y1 = field.getRows() * cellSize;
			canvas.drawLine(x0, y0, x1, y1, paint);
		}
	}

	private Paint getWhitePaint() {
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);

		return paint;
	}

	private void drawCellBalls(Canvas canvas) {
		for (Cell cell : field.getCells()) {
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
		}

		//		throw new IllegalArgumentException();
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

		if (field.cellExists(row, col)) {
			Cell touchedCell = field.getCell(row, col);
			if (noSelectedCell() && touchedCell.isEmpty() == false) {
				selectedCell = touchedCell;
			} else {
				tryMoveBallTo(touchedCell);
				clearSelectedCell();
			}
		} else {
			clearSelectedCell();
		}

		postInvalidate();

		return super.onTouchEvent(event);
	}

	private boolean noSelectedCell() {
		return selectedCell == null;
	}

	private void clearSelectedCell() {
		selectedCell = null;
	}

	private void tryMoveBallTo(Cell destinationCell) {
		try {
			field.moveBall(selectedCell, destinationCell);
		} catch (Exception exp) {
		}
	}
}
