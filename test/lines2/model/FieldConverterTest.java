package lines2.model;

import junit.framework.TestCase;
import lines2.common.TestUtils;
import static lines2.model.PathChecker.*;

public class FieldConverterTest extends TestCase {

	public void testToPathCheckerField() {
		final int rows = 2;
		final int cols = 3;
		Field field = new Field(rows, cols);
		field.getCell(0, 2).setBall(TestUtils.getBall());
		field.getCell(1, 0).setBall(TestUtils.getBall());
		field.getCell(1, 2).setBall(TestUtils.getBall());

		int[][] actual = FieldConverter.toPathCheckerField(field);
		assertEquals(EMPTY, actual[0][0]);
		assertEquals(EMPTY, actual[0][1]);
		assertEquals(WALL, actual[0][2]);

		assertEquals(WALL, actual[1][0]);
		assertEquals(EMPTY, actual[1][1]);
		assertEquals(WALL, actual[1][2]);
	}
}
