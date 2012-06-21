package lines2.model;

import lines2.utils.IterableMatrix;

public class Field {

	private int rows, cols;
	private Cell[][] cells;
	private MoveStrategy moveStrategy;

	public Field(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		initCells();
	}

	public MoveStrategy getMoveStrategy() {
		return moveStrategy;
	}

	public void setMoveStrategy(MoveStrategy moveStrategy) {
		this.moveStrategy = moveStrategy;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public Cell getCell(int row, int col) {
		return cells[row][col];
	}

	public Iterable<Cell> getCells() {
		return new IterableMatrix<Cell>(cells);
	}

	public boolean cellExists(int row, int col) {
		return (0 <= row && row < rows) && (0 <= col && col < cols);
	}

	public void moveBall(Cell from, Cell to) {
		if (from == to)
			throw new IllegalArgumentException();
		if (from.isEmpty())
			throw new IllegalArgumentException();
		if (to.isEmpty() == false)
			throw new IllegalArgumentException();

		boolean result = moveStrategy.checkMove(this, from.getRow(), from.getCol(), to.getRow(), to.getCol());

		if (result) {
			Ball ball = from.getBall();
			from.clearBall();
			to.setBall(ball);
		}
	}

	private void initCells() {
		cells = new Cell[rows][cols];
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				cells[r][c] = new Cell(r, c);
	}
}
