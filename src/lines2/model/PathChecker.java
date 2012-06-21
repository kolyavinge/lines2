package lines2.model;

class PathChecker {

	public static final int EMPTY = 0;
	public static final int WALL = 1;
	private static final int VISIT = 2;

	private int startRow, startCol;
	private int finishRow, finishCol;
	private int[][] field;
	private boolean endFlag;

	public boolean check() {
		validateInputValues();
		endFlag = false;
		checkRec(startRow, startCol);

		return endFlag;
	}

	private void checkRec(int row, int col) {
		if (endFlag || !cellExists(row, col))
			return;

		if (isFinishPoint(row, col))
			endFlag = true;

		if (!isVisited(row, col) && isEmpty(row, col)) {
			visitCell(row, col);
			checkRec(row - 1, col);
			checkRec(row, col - 1);
			checkRec(row + 1, col);
			checkRec(row, col + 1);
		}
	}

	private boolean isEmpty(int row, int col) {
		return field[row][col] == EMPTY;
	}

	private boolean cellExists(int row, int col) {
		return (0 <= row) && (row < field.length) && (0 <= col) && (col < field[row].length);
	}

	private void visitCell(int row, int col) {
		field[row][col] = VISIT;
	}

	private boolean isVisited(int row, int col) {
		return field[row][col] == VISIT;
	}

	private boolean isFinishPoint(int row, int col) {
		return row == finishRow && col == finishCol;
	}

	private void validateField() {
		if (field == null)
			throw new IllegalArgumentException("field is not initialize");
	}

	private void validateStartPoint() {
		if (!cellExists(startRow, startCol))
			throw new IllegalArgumentException("start point out of field");
	}

	private void validateFinishPoint() {
		if (!cellExists(finishRow, finishCol))
			throw new IllegalArgumentException("finish point out of field");
	}

	private void validateInputValues() {
		validateField();
		validateStartPoint();
		validateFinishPoint();
	}

	public int[][] getField() {
		return field;
	}

	public void setField(int[][] field) {
		this.field = field;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getStartCol() {
		return startCol;
	}

	public void setStartPoint(int row, int col) {
		startRow = row;
		startCol = col;
	}

	public int getFinishRow() {
		return finishRow;
	}

	public int getFinishCol() {
		return finishCol;
	}

	public void setFinishPoint(int row, int col) {
		finishRow = row;
		finishCol = col;
	}
}
