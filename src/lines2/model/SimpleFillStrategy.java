package lines2.model;

import java.util.ArrayList;
import java.util.Random;

class SimpleFillStrategy implements FillStrategy {

	private Random rand;
	private int maxColorNumber;
	private int left, right;

	public SimpleFillStrategy(int maxColorNumber) {
		this.maxColorNumber = maxColorNumber;
		this.rand = new Random();
	}

	public void setFillRange(int left, int right) {
		this.left = left;
		this.right = right;
	}

	public void fill(Field field) {
		int cellsToFillCount = left + rand.nextInt(right - left + 1);

		if (cellsToFillCount > getEmptyCellsCount(field))
			throw new FieldOverflowException();

		for (int i = 0; i < cellsToFillCount; i++) {
			Ball ball = getRandomBall();
			Cell cell = getRandomCell(field);
			cell.setBall(ball);
		}
	}

	private Cell getRandomCell(Field field) {
		ArrayList<Cell> emptyCells = getEmptyCells(field);
		int cellIndex = rand.nextInt(emptyCells.size());
		Cell cell = emptyCells.get(cellIndex);

		return cell;
	}

	private Ball getRandomBall() {
		int num = rand.nextInt(maxColorNumber);
		Color color = Color.getColorByNumber(num);

		return new ColoredBall(color);
	}

	private ArrayList<Cell> getEmptyCells(Field field) {
		ArrayList<Cell> result = new ArrayList<Cell>();
		for (Cell c : field.getCells())
			if (c.isEmpty())
				result.add(c);

		return result;
	}

	private int getEmptyCellsCount(Field field) {
		int count = 0;
		for (Cell c : field.getCells())
			if (c.isEmpty())
				count++;

		return count;
	}
}
