package lines2.model;

public class FieldLoader {

	public Field getField(int rows, int cols) {
		Field field = new Field(rows, cols);

		field.setMoveStrategy(new SimpleMoveStrategy());

		field.setEraseStrategy(new StraightEraseStrategy(4));

		SimpleFillStrategy fillStrategy = new SimpleFillStrategy(3);
		fillStrategy.setFillRange(3, 4);
		field.setFillStrategy(fillStrategy);

		field.populate();

		return field;
	}
}
