package lines2.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Field extends FieldListenerManager {

	private CellMatrix cellMatrix;
	private MoveStrategy moveStrategy;
	private EraseStrategy eraseStrategy;
	private FillStrategy fillStrategy;
	private Collection<Ball> nextBalls = Collections.emptyList();

	public Field(int rows, int cols) {
		cellMatrix = new CellMatrix(rows, cols);
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
		return cellMatrix.getCellsCount() == cellMatrix.getEmptyCellsCount();
	}

	public Collection<Ball> getNextBalls() {
		return nextBalls;
	}

	public int getRows() {
		return cellMatrix.getRows();
	}

	public int getCols() {
		return cellMatrix.getCols();
	}

	public Cell getCell(int row, int col) {
		return cellMatrix.getCell(row, col);
	}

	public Iterable<Cell> getCells() {
		return cellMatrix.getCells();
	}

	public Iterable<Cell> getEmptyCells() {
		return cellMatrix.getEmptyCells();
	}

	public Iterable<Cell> getNonEmptyCells() {
		return cellMatrix.getNonEmptyCells();
	}

	public boolean cellExists(int row, int col) {
		return cellMatrix.cellExists(row, col);
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
	
	private void verifyMoveBallArgs(Cell from, Cell to) {
		if (from == to)
			throw new IllegalArgumentException();

		if (from.isEmpty())
			throw new IllegalArgumentException();

		if (to.isEmpty() == false)
			throw new IllegalArgumentException();
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
}
