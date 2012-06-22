package lines2.model;

import java.util.*;

import lines2.utils.Point;

class StraightEraseStrategy implements EraseStrategy {

	private int minEraseLineLength;

	public StraightEraseStrategy(int eraseLineLength) {
		this.minEraseLineLength = eraseLineLength;
	}

	public Collection<Cell> getErasedCells(Field field, Cell lastStepCell) {
		int[][] convertedField = FieldConverter.toEraserField(field);
		Eraser eraser = new Eraser();
		eraser.setField(convertedField);
		eraser.setLastStepPoint(lastStepCell.getRow(), lastStepCell.getCol());
		eraser.setMinEraseLineLength(minEraseLineLength);

		Collection<Point> erasedCells = eraser.getErasedCells();

		Collection<Cell> result = new ArrayList<Cell>(erasedCells.size());
		for (Point p : erasedCells) {
			Cell cell = field.getCell(p.getY(), p.getX());
			result.add(cell);
		}

		return result;
	}
}
