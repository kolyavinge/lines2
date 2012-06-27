package lines2.app;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GridRenderer {

	private static final Paint whitePaint;
	private Canvas canvas;
	private float cellSize;
	private int fieldRows, fieldCols;

	static {
		whitePaint = new Paint();
		whitePaint.setColor(Color.WHITE);
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public void setCellSize(float cellSize) {
		this.cellSize = cellSize;
	}

	public void setFieldSize(int rows, int cols) {
		this.fieldRows = rows;
		this.fieldCols = cols;
	}

	public void drawGrid() {
		drawHorizontalLines();
		drawVerticalLines();
	}

	private void drawHorizontalLines() {
		for (int row = 0; row <= fieldRows; row++) {
			float x0 = 0;
			float y0 = row * cellSize;
			float x1 = fieldCols * cellSize;
			float y1 = y0;
			canvas.drawLine(x0, y0, x1, y1, whitePaint);
		}
	}

	private void drawVerticalLines() {
		for (int col = 0; col <= fieldCols; col++) {
			float x0 = col * cellSize;
			float y0 = 0;
			float x1 = x0;
			float y1 = fieldRows * cellSize;
			canvas.drawLine(x0, y0, x1, y1, whitePaint);
		}
	}
}
