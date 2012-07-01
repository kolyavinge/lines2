package lines2.app;

import lines2.model.Cell;
import lines2.model.Field;

public class FieldPresenter {

	private Field field;
	private Cell selectedCell;

	public FieldPresenter(Field field) {
		this.field = field;
	}

	public Field getField() {
		return field;
	}

	public Iterable<Cell> getFieldCells() {
		return field.getCells();
	}

	public void selectCell(int row, int col) {
		if (!field.cellExists(row, col)) {
			clearSelectedCell();
			return;
		}

		Cell currentCell = field.getCell(row, col);

		if (!currentCell.isEmpty()) {
			selectedCell = currentCell;
		} else if (!noSelectedCell() && currentCell.isEmpty()) {
			tryMoveBallTo(currentCell);
			clearSelectedCell();
		}
	}

	private void tryMoveBallTo(Cell destinationCell) {
		try {
			field.moveBall(selectedCell, destinationCell);
		} catch (Exception exp) {
		}
	}

	public Cell getSelectedCell() {
		return selectedCell;
	}

	public boolean noSelectedCell() {
		return selectedCell == null;
	}

	private void clearSelectedCell() {
		selectedCell = null;
	}
}
