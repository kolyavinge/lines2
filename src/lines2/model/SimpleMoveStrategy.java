package lines2.model;

class SimpleMoveStrategy implements MoveStrategy {

	public boolean checkMove(Field field, int startRow, int startCol, int finishRow, int finishCol) {
		PathChecker checker = new PathChecker();

		int[][] convertedField = FieldConverter.toPathCheckerField(field);
		convertedField[startRow][startCol] = PathChecker.EMPTY;

		checker.setField(convertedField);
		checker.setStartPoint(startRow, startCol);
		checker.setFinishPoint(finishRow, finishCol);

		return checker.check();
	}
}
