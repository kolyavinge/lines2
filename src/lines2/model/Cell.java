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

	void setPosition(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public Ball getBall() {
		return ball;
	}

	protected void setBall(Ball ball) {
		clear();
		this.ball = ball;
		this.ball.setCell(this);
	}

	void clear() {
		if (isEmpty() == false) {
			ball.setCell(null);
			ball = null;
		}
	}

	public boolean isEmpty() {
		return ball == null;
	}
}
