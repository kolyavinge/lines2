package lines2.model;

import java.util.Collection;

public interface FieldListener {
	
	void onMoveBall(Cell from, Cell to);
	
	void onIllegalMoveBall(Cell from, Cell to);

	void onFillCells(Collection<Cell> filledCells);

	void onEraseCells(Collection<Cell> erasedCells);
}
