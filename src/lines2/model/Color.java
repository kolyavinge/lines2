package lines2.model;

import java.util.Random;

public enum Color {

	RED(0),
	GREEN(1),
	BLUE(2);

	public static Color getRandomColor() {
		Random rand = new Random();
		return getColorByNumber(rand.nextInt(3));
	}

	public static Color getColorByNumber(int number) {
		switch (number) {
		case 0:
			return RED;
		case 1:
			return GREEN;
		case 2:
			return BLUE;
		}

		throw new IllegalArgumentException();
	}

	private int number;

	private Color(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}
}
