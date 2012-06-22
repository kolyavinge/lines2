package lines2.model;

import junit.framework.TestCase;
import lines2.common.TestUtils;

public class FieldConverterTest extends TestCase {

	public void testToPathCheckerField() {
		final int rows = 2;
		final int cols = 3;
		Field field = new Field(rows, cols);
		field.getCell(0, 2).setBall(TestUtils.getBall());
		field.getCell(1, 0).setBall(TestUtils.getBall());
		field.getCell(1, 2).setBall(TestUtils.getBall());

		int[][] actual = FieldConverter.toPathCheckerField(field);
		assertEquals(PathChecker.EMPTY, actual[0][0]);
		assertEquals(PathChecker.EMPTY, actual[0][1]);
		assertEquals(PathChecker.WALL, actual[0][2]);

		assertEquals(PathChecker.WALL, actual[1][0]);
		assertEquals(PathChecker.EMPTY, actual[1][1]);
		assertEquals(PathChecker.WALL, actual[1][2]);
	}

	public void testToEraserField() {
		final int rows = 2;
		final int cols = 3;
		Field field = new Field(rows, cols);
		field.getCell(0, 2).setBall(new ColoredBall(Color.RED));
		field.getCell(1, 0).setBall(new ColoredBall(Color.GREEN));
		field.getCell(1, 2).setBall(TestUtils.getBall());

		int[][] actual = FieldConverter.toEraserField(field);
		assertEquals(Eraser.EMPTY, actual[0][0]);
		assertEquals(Eraser.EMPTY, actual[0][1]);
		assertEquals(Color.RED.getNumber(), actual[0][2]);

		assertEquals(Color.GREEN.getNumber(), actual[1][0]);
		assertEquals(Eraser.EMPTY, actual[1][1]);
		assertEquals(Eraser.EMPTY, actual[1][2]);
	}
}
