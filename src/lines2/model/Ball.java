package lines2.model;

public abstract class Ball {

	private BallType ballType;
	private Cell cell;

	public Ball(BallType ballType) {
		this.ballType = ballType;
	}

	public final Cell getCell() {
		return cell;
	}

	final void setCell(Cell cell) {
		this.cell = cell;
	}

	public final BallType getType() {
		return ballType;
	}
}
