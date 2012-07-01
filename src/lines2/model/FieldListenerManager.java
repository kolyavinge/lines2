package lines2.model;

import java.util.*;

class FieldListenerManager {

	private Collection<FieldListener> fieldListeners = new ArrayList<FieldListener>();

	public void addFieldListener(FieldListener fieldListener) {
		this.fieldListeners.add(fieldListener);
	}

	public void removeFieldListener(FieldListener fieldListener) {
		this.fieldListeners.remove(fieldListener);
	}

	protected void raiseOnMoveBall(Cell from, Cell to) {
		for (FieldListener fieldListener : fieldListeners)
			fieldListener.onMoveBall(from, to);
	}
	
	protected void raiseOnIllegalMoveBall(Cell from, Cell to) {
		for (FieldListener fieldListener : fieldListeners)
			fieldListener.onIllegalMoveBall(from, to);
	}

	protected void raiseOnFillCells(Collection<Cell> filledCells) {
		for (FieldListener fieldListener : fieldListeners)
			fieldListener.onFillCells(filledCells);
	}

	protected void raiseOnEraseCells(Collection<Cell> erasedCells) {
		for (FieldListener fieldListener : fieldListeners)
			fieldListener.onEraseCells(erasedCells);
	}
}
