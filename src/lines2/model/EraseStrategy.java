package lines2.model;

import java.util.Collection;

public interface EraseStrategy {

	Collection<Cell> getErasedCells(Field field, Cell lastStepCell);
}
