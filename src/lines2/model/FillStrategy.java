package lines2.model;

import java.util.*;

public interface FillStrategy {

	Collection<Ball> getNextFillCells(Iterable<Cell> cells);
}
