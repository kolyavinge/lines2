package lines2.app;

import lines2.model.*;

class FieldPresenter {

	private Field field;
	private MoveBallAction moveBallAction;
	private Cell selectedCell;

	public FieldPresenter(Field field, MoveBallAction moveBallAction) {
		this.field = field;
		this.moveBallAction = moveBallAction;
	}

	public Field getField() {
		return field;
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
			moveBallAction.moveBall(selectedCell, currentCell);
			clearSelectedCell();
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
