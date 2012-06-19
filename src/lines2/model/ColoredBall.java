package lines2.model;

public class ColoredBall implements Ball {

	private Color color;

	public ColoredBall(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public BallType getType() {
		return BallType.COLORED_BALL;
	}
}
