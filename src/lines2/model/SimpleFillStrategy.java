package lines2.model;

import java.util.*;

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

	public Map<Cell, Ball> getNextFillCells(Iterable<Cell> cells) {
		Map<Cell, Ball> nextCells = new HashMap<Cell, Ball>();

		ArrayList<Cell> emptyCells = getEmptyCells(cells);
		int cellsToFillCount = left + rand.nextInt(right - left + 1);

		for (int i = 0; i < cellsToFillCount && !emptyCells.isEmpty(); i++) {
			Cell cell = getRandomEmptyCell(emptyCells);
			emptyCells.remove(cell);
			Ball ball = getRandomBall();
			nextCells.put(cell, ball);
		}

		return nextCells;
	}

	private Ball getRandomBall() {
		int num = rand.nextInt(maxColorNumber);
		Color color = Color.getColorByNumber(num);

		return new ColoredBall(color);
	}

	private Cell getRandomEmptyCell(List<Cell> emptyCells) {
		int cellIndex = rand.nextInt(emptyCells.size());
		Cell cell = emptyCells.get(cellIndex);

		return cell;
	}

	private ArrayList<Cell> getEmptyCells(Iterable<Cell> cells) {
		ArrayList<Cell> result = new ArrayList<Cell>();
		for (Cell c : cells)
			if (c.isEmpty())
				result.add(c);

		return result;
	}
}
