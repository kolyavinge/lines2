package lines2.model;

import java.util.*;
import lines2.utils.IterableMatrix;

public class Field {

	private int rows, cols;
	private Cell[][] cells;
	private MoveStrategy moveStrategy;
	private EraseStrategy eraseStrategy;
	private FillStrategy fillStrategy;
	private Collection<Ball> nextBalls = Collections.emptyList();
	private Collection<FieldListener> fieldListeners = new ArrayList<FieldListener>();

	public Field(int rows, int cols) {
		setSize(rows, cols);
		initCells();
	}

	private void setSize(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
	}

	public void setMoveStrategy(MoveStrategy moveStrategy) {
		this.moveStrategy = moveStrategy;
	}

	public void setEraseStrategy(EraseStrategy eraseStrategy) {
		this.eraseStrategy = eraseStrategy;
	}

	public void setFillStrategy(FillStrategy fillStrategy) {
		this.fillStrategy = fillStrategy;
		generateNextBalls();
	}

	public boolean isEmpty() {
		return getEmptyCellsCount() == (rows * cols);
	}

	public Collection<Ball> getNextBalls() {
		return nextBalls;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public Cell getCell(int row, int col) {
		try {
			return cells[row][col];
		} catch (Exception exp) {
			throw new IllegalArgumentException(String.format("No cell on [%d:%d]", row, col));
		}
	}

	public Iterable<Cell> getCells() {
		return new IterableMatrix<Cell>(cells);
	}

	public Iterable<Cell> getEmptyCells() {
		Collection<Cell> result = new ArrayList<Cell>();
		for (Cell cell : getCells()) {
			if (cell.isEmpty())
				result.add(cell);
		}

		return result;
	}

	public Iterable<Cell> getNonEmptyCells() {
		Collection<Cell> result = new ArrayList<Cell>();
		for (Cell cell : getCells()) {
			if (cell.isEmpty() == false)
				result.add(cell);
		}

		return result;
	}

	public boolean cellExists(int row, int col) {
		return (0 <= row && row < rows) && (0 <= col && col < cols);
	}

	public void moveBall(Cell from, Cell to) {
		verifyMoveBallArgs(from, to);
		if (checkMove(from, to) == false)
			return;

		swapCells(from, to);
		raiseOnBallMove(from, to);
		if (eraseCells(to) == false) {
			fillNextCells();
			generateNextBalls();
		}

		// после стирания шаров поле может оказаться пустым
		// в этом случае заполняем его
		if (isEmpty())
			populate();
	}

	void populate() {
		if (nextBalls.isEmpty())
			generateNextBalls();

		fillNextCells();
		generateNextBalls();

		fillNextCells();
		generateNextBalls();
	}

	private void generateNextBalls() {
		nextBalls = fillStrategy.getNextBalls(getCells());
	}

	private void fillNextCells() {
		Collection<Cell> filledCells = new ArrayList<Cell>();

		for (Ball nextBall : nextBalls) {
			Cell cell = nextBall.getCell();
			if (cell.isEmpty()) {
				filledCells.add(cell);
				cell.setBall(nextBall);
			}
		}

		raiseOnFillCells(filledCells);

		// если после появления шарика образуется линия, то ее нужно стереть
		for (Cell cell : filledCells)
			eraseCells(cell);
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

	private void swapCells(Cell from, Cell to) {
		Ball ball = from.getBall();
		from.clear();
		to.setBall(ball);
	}

	private boolean eraseCells(Cell lastStepCell) {
		Collection<Cell> erasedCells = eraseStrategy.getErasedCells(this, lastStepCell);
		if (erasedCells.isEmpty())
			return false;

		raiseOnEraseCells(erasedCells);

		for (Cell cell : erasedCells)
			cell.clear();

		return true;
	}

	private void initCells() {
		cells = new Cell[rows][cols];
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				cells[r][c] = new Cell(r, c);
	}

	int getEmptyCellsCount() {
		int count = 0;
		for (Cell c : getCells())
			if (c.isEmpty())
				count++;

		return count;
	}

	public void addFieldListener(FieldListener fieldListener) {
		this.fieldListeners.add(fieldListener);
	}

	public void removeFieldListener(FieldListener fieldListener) {
		this.fieldListeners.remove(fieldListener);
	}

	private void raiseOnBallMove(Cell from, Cell to) {
		for (FieldListener fieldListener : fieldListeners)
			fieldListener.onBallMove(from, to);
	}

	private void raiseOnFillCells(Collection<Cell> filledCells) {
		for (FieldListener fieldListener : fieldListeners)
			fieldListener.onFillCells(filledCells);
	}

	private void raiseOnEraseCells(Collection<Cell> erasedCells) {
		for (FieldListener fieldListener : fieldListeners)
			fieldListener.onEraseCells(erasedCells);
	}
}
