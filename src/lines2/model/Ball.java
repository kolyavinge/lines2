package lines2.model;

public abstract class Ball {

	private BallType ballType;
	private Cell cell;

	public Ball(BallType ballType) {
		this.ballType = ballType;
	}

	public Cell getCell() {
		return cell;
	}

	void setCell(Cell cell) {
		this.cell = cell;
	}

	public BallType getType() {
		return ballType;
	}
}
