package lines2.model;

class Level {

	private int number;
	private int totalLevelScores;
	private int ballColorsCount;
	private int fieldRows, fieldCols;
	private int eraseLineLength;
	private FillStrategy fillStrategy;
	private EraseStrategy eraseStrategy;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getTotalLevelScores() {
		return totalLevelScores;
	}

	public void setTotalLevelScores(int totalLevelScores) {
		this.totalLevelScores = totalLevelScores;
	}

	public int getBallColorsCount() {
		return ballColorsCount;
	}

	public void setBallColorsCount(int ballColorsCount) {
		this.ballColorsCount = ballColorsCount;
	}

	public int getFieldRows() {
		return fieldRows;
	}

	public void setFieldRows(int fieldRows) {
		this.fieldRows = fieldRows;
	}

	public int getFieldCols() {
		return fieldCols;
	}

	public void setFieldCols(int fieldCols) {
		this.fieldCols = fieldCols;
	}

	public int getEraseLineLength() {
		return eraseLineLength;
	}

	public void setEraseLineLength(int eraseLineLength) {
		this.eraseLineLength = eraseLineLength;
	}

	public FillStrategy getFillStrategy() {
		return fillStrategy;
	}

	public void setFillStrategy(FillStrategy fillStrategy) {
		this.fillStrategy = fillStrategy;
	}

	public EraseStrategy getEraseStrategy() {
		return eraseStrategy;
	}

	public void setEraseStrategy(EraseStrategy eraseStrategy) {
		this.eraseStrategy = eraseStrategy;
	}
}
