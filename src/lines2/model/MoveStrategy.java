package lines2.model;

public interface MoveStrategy {

	boolean checkMove(Field field, int startRow, int startCol, int finishRow, int finishCol);
}
