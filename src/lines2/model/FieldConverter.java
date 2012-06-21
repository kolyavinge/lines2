package lines2.model;

final class FieldConverter {

	public static int[][] toPathCheckerField(Field field) {
		int[][] result = new int[field.getRows()][field.getCols()];

		for (Cell c : field.getCells())
			result[c.getRow()][c.getCol()] = c.isEmpty() ? PathChecker.EMPTY : PathChecker.WALL;

		return result;
	}
}
