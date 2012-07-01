package lines2.model;

import java.util.Random;

public enum Color {

	RED(0),
	GREEN(1),
	BLUE(2),
	ORANGE(3),
	PURPLE(4);
	
	private static final int count = 5;
	
	public static int getColorsCount() {
		return count;
	}

	public static Color getRandomColor() {
		Random rand = new Random();
		return getColorByNumber(rand.nextInt(count));
	}

	public static Color getColorByNumber(int number) {
		switch (number) {
		case 0:
			return RED;
		case 1:
			return GREEN;
		case 2:
			return BLUE;
		case 3:
			return ORANGE;
		case 4:
			return PURPLE;
		}

		throw new IllegalArgumentException(String.format("No color with number: %d", number));
	}

	private int number;

	private Color(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}
}
