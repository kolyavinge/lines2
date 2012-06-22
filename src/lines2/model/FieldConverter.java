package lines2.model;

final class FieldConverter {

	public static int[][] toPathCheckerField(Field field) {
		int[][] result = new int[field.getRows()][field.getCols()];

		for (Cell c : field.getCells())
			result[c.getRow()][c.getCol()] = c.isEmpty() ? PathChecker.EMPTY : PathChecker.WALL;

		return result;
	}

	public static int[][] toEraserField(Field field) {
		int[][] result = new int[field.getRows()][field.getCols()];

		for (Cell cell : field.getCells()) {
			int row = cell.getRow();
			int col = cell.getCol();
			if (!cell.isEmpty() && cell.getBall().getType() == BallType.COLORED_BALL) {
				ColoredBall ball = (ColoredBall) cell.getBall();
				result[row][col] = ball.getColor().getNumber();
			} else {
				result[row][col] = Eraser.EMPTY;
			}
		}

		return result;
	}
}
