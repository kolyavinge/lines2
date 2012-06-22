package lines2.model;

public interface MoveStrategy {

	boolean checkMove(Field field, Cell startCell, Cell finishCell);
}
