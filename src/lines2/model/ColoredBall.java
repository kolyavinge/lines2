package lines2.model;

public class ColoredBall extends Ball {

	private Color color;

	public ColoredBall(Color color) {
		super(BallType.COLORED_BALL);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
