package lines2.model;

import java.util.Random;

public class FieldLoader {

	private Random rand = new Random();

	public Field getField(int rows, int cols) {
		Field field = new Field(rows, cols);
		field.setMoveStrategy(new SimpleMoveStrategy());
		field.setEraseStrategy(new StraightEraseStrategy(4));

		for (int i = 0; i < 8; i++) {
			int row = rand.nextInt(field.getRows());
			int col = rand.nextInt(field.getCols());
			Cell cell = field.getCell(row, col);
			Ball ball = new ColoredBall(Color.getRandomColor());
			cell.setBall(ball);
		}

		return field;
	}
}
