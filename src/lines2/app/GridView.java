package lines2.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class GridView extends View {

	private int rows, cols;
	private boolean selectedCellExist;
	private int selectedRow, selectedCol;
	private float cellSize;
	private int gridColor, selectedCellColor;

	public GridView(Context context) {
		super(context);
		clearSelectedCell();
	}

	public void setSize(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
	}

	public float getCellSize() {
		return cellSize;
	}

	public void setGridColor(int gridColor) {
		this.gridColor = gridColor;
	}

	public void setSelectedCellColor(int selectedCellColor) {
		this.selectedCellColor = selectedCellColor;
	}

	public void setSelectedCell(int row, int col) {
		this.selectedRow = row;
		this.selectedCol = col;
		this.selectedCellExist = true;
	}

	public void clearSelectedCell() {
		selectedCellExist = false;
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		float width = right - left;
		float height = bottom - top;
		calculateCellSize(width, height);
	}

	private void calculateCellSize(float width, float height) {
		float cellSizeHeight = height / rows - 0.1f;
		float cellSizeWidth = width / cols - 0.1f;
		cellSize = (cellSizeHeight * cols < width) ? cellSizeHeight : cellSizeWidth;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (selectedCellExist)
			drawSelectedCell(canvas);
		drawHorizontalLines(canvas);
		drawVerticalLines(canvas);
	}

	private void drawHorizontalLines(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(gridColor);
		for (int row = 0; row <= rows; row++) {
			float x0 = 0;
			float y0 = row * cellSize;
			float x1 = cols * cellSize;
			float y1 = y0;
			canvas.drawLine(x0, y0, x1, y1, paint);
		}
	}

	private void drawVerticalLines(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(gridColor);
		for (int col = 0; col <= cols; col++) {
			float x0 = col * cellSize;
			float y0 = 0;
			float x1 = x0;
			float y1 = rows * cellSize;
			canvas.drawLine(x0, y0, x1, y1, paint);
		}
	}

	private void drawSelectedCell(Canvas canvas) {
		RectF rect = getSelectedCellRect();
		Paint paint = new Paint();
		paint.setColor(selectedCellColor);
		canvas.drawRect(rect, paint);
	}

	private RectF getSelectedCellRect() {
		return new RectF(
				selectedCol * cellSize,
				selectedRow * cellSize,
				selectedCol * cellSize + cellSize,
				selectedRow * cellSize + cellSize);
	}
}
