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

	void populate() {
		if (nextBalls.isEmpty())
			generateNextBalls();

		fillNextCells();
		generateNextBalls();

		fillNextCells();
		generateNextBalls();
	}

	void moveBall(Cell fromCell, Cell toCell) {
		if (moveBallArgsCorrect(fromCell, toCell) == false)
			throw new MoveBallException();

		if (checkPathInField(fromCell, toCell) == false) {
			raiseOnIllegalMoveBall(fromCell, toCell);
			return;
		}

		moveBallToCell(fromCell, toCell);
		raiseOnMoveBall(fromCell, toCell);

		if (tryEraseLine(toCell) == false) {
			Collection<Cell> filledCells = fillNextCells();
			tryEraseLinesForCells(filledCells);
			clearNextBalls();
			verifyFullField();
			generateNextBalls();
		}

		boolean fieldIsEmpty = cellMatrix.getCellsCount() == cellMatrix.getEmptyCellsCount();
		if (fieldIsEmpty)
			populate();
	}

	private boolean checkPathInField(Cell from, Cell to) {
		return moveStrategy.checkMove(this, from, to);
	}

	private void moveBallToCell(Cell from, Cell to) {
		Ball ball = from.getBall();
		from.clear();
		to.setBall(ball);
	}

	private boolean tryEraseLine(Cell lastStepCell) {
		Collection<Cell> erasedCells = eraseStrategy.getErasedCells(this, lastStepCell);
		if (erasedCells.isEmpty())
			return false;

		raiseOnEraseCells(erasedCells);

		for (Cell cell : erasedCells)
			cell.clear();

		return true;
	}

	private void tryEraseLinesForCells(Iterable<Cell> filledCells) {
		for (Cell cell : filledCells)
			tryEraseLine(cell);
	}

	private Collection<Cell> fillNextCells() {
		Collection<Cell> filledCells = new ArrayList<Cell>();

		for (Ball nextBall : nextBalls) {
			Cell cell = nextBall.getCell();
			if (cell.isEmpty()) {
				cell.setBall(nextBall);
				filledCells.add(cell);
			}
		}

		raiseOnFillCells(filledCells);

		return filledCells;
	}

	private void generateNextBalls() {
		nextBalls = fillStrategy.getNextBalls(getCells());
	}
	
	private void clearNextBalls() {
		nextBalls.clear();
	}

	private void verifyFullField() {
		if (cellMatrix.getEmptyCellsCount() < 2)
			throw new FieldOverflowException();
	}

	private boolean moveBallArgsCorrect(Cell from, Cell to) {
		boolean result = from != to;
		result &= from.isEmpty() == false;
		result &= to.isEmpty();

		return result;
	}
}
