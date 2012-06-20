package lines2.app;

import lines2.model.Cell;
import lines2.model.Field;
import android.view.View;

public class FieldPresenter {
	
	private Field field;
	private View view;
	private Cell selectedCell;

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public void selectCell(int row, int col) {
		if (field.cellExists(row, col)) {
			Cell currentCell = field.getCell(row, col);
			if (noSelectedCell() && currentCell.isEmpty() == false) {
				selectedCell = currentCell;
			} else {
				tryMoveBallTo(currentCell);
				clearSelectedCell();
			}
		} else {
			clearSelectedCell();
		}

		view.postInvalidate();
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
