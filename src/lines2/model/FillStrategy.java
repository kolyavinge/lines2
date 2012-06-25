package lines2.model;

import java.util.*;

public interface FillStrategy {

	Map<Cell, Ball> getNextFillCells(Iterable<Cell> cells);
}
