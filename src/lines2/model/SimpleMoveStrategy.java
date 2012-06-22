package lines2.model;

class SimpleMoveStrategy implements MoveStrategy {

	private PathChecker checker = new PathChecker();

	public boolean checkMove(Field field, Cell startCell, Cell finishCell) {
		int startRow = startCell.getRow();
		int startCol = startCell.getCol();
		int finishRow = finishCell.getRow();
		int finishCol = finishCell.getCol();

		int[][] convertedField = FieldConverter.toPathCheckerField(field);
		convertedField[startRow][startCol] = PathChecker.EMPTY;

		checker.setField(convertedField);
		checker.setStartPoint(startRow, startCol);
		checker.setFinishPoint(finishRow, finishCol);

		return checker.check();
	}
}
