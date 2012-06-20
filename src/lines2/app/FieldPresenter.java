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

	public void selectCell(int row, int col) {
		if (field.cellExists(row, col)) {
			Cell currentCell = field.getCell(row, col);
			if (noSelectedCell() && !currentCell.isEmpty()) {
				selectedCell = currentCell;
			} else {
				tryMoveBallTo(currentCell);
				clearSelectedCell();
			}
		} else {
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
