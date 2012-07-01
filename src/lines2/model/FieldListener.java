package lines2.model;

import java.util.Collection;

public interface FieldListener {
	
	void onBallMove(Cell from, Cell to);

	void onFillCells(Collection<Cell> filledCells);

	void onEraseCells(Collection<Cell> erasedCells);
}
