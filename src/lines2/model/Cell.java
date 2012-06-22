package lines2.model;

public class Cell {

	private int row, col;
	private Ball ball;

	public Cell() {
		this(0, 0);
	}

	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void setPosition(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}

	public void clear() {
		ball = null;
	}

	public boolean isEmpty() {
		return ball == null;
	}
}
