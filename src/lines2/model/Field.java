package lines2.model;

import java.util.ArrayList;
import java.util.Collection;

import lines2.utils.IterableMatrix;

public class Field {

	private int rows, cols;
	private Cell[][] cells;
	private MoveStrategy moveStrategy;
	private EraseStrategy eraseStrategy;
	private FillStrategy fillStrategy;
	private Collection<FieldListener> fieldListeners = new ArrayList<FieldListener>();

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

	public EraseStrategy getEraseStrategy() {
		return eraseStrategy;
	}

	public void setEraseStrategy(EraseStrategy eraseStrategy) {
		this.eraseStrategy = eraseStrategy;
	}

	public FillStrategy getFillStrategy() {
		return fillStrategy;
	}

	public void setFillStrategy(FillStrategy fillStrategy) {
		this.fillStrategy = fillStrategy;
	}

	public void addFieldListener(FieldListener fieldListener) {
		this.fieldListeners.add(fieldListener);
	}
	
	public void removeFieldListener(FieldListener fieldListener) {
		this.fieldListeners.remove(fieldListener);
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
		verifyMoveBallArgs(from, to);
		if (checkMove(from, to)) {
			swapBalls(from, to);
			if (eraseCells(to) == false)
				fill();
		}
	}

	private void fill() {
		fillStrategy.fill(this);
	}

	private boolean checkMove(Cell from, Cell to) {
		return moveStrategy.checkMove(this, from, to);
	}

	private void verifyMoveBallArgs(Cell from, Cell to) {
		if (from == to)
			throw new IllegalArgumentException();

		if (from.isEmpty())
			throw new IllegalArgumentException();

		if (to.isEmpty() == false)
			throw new IllegalArgumentException();
	}

	private void swapBalls(Cell from, Cell to) {
		Ball ball = from.getBall();
		from.clear();
		to.setBall(ball);
	}

	private boolean eraseCells(Cell lastStepCell) {
		Collection<Cell> erasedCells = eraseStrategy.getErasedCells(this, lastStepCell);
		if (erasedCells.isEmpty() == false) {
			for (Cell cell : erasedCells)
				cell.clear();

			for (FieldListener fieldListener : fieldListeners)
				fieldListener.onEraseCells(erasedCells);
		}

		return !erasedCells.isEmpty();
	}

	private void initCells() {
		cells = new Cell[rows][cols];
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				cells[r][c] = new Cell(r, c);
	}
}
