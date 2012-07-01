package lines2.model;

import java.util.*;

public interface FillStrategy {

	Collection<Ball> getNextBalls(Iterable<Cell> cells);
}
