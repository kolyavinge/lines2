package lines2.model;

import java.util.*;

import lines2.utils.Point;

class StraightEraseStrategy implements EraseStrategy {

	private int minEraseLineLength;
	private Eraser eraser;

	public StraightEraseStrategy(int eraseLineLength) {
		this.minEraseLineLength = eraseLineLength;
		this.eraser = new Eraser();
		this.eraser.setMinEraseLineLength(minEraseLineLength);
	}

	public Collection<Cell> getErasedCells(Field field, Cell lastStepCell) {
		Collection<Point> erasedCells = getResultFromEraser(field, lastStepCell);
		return convertToCellCollection(field, erasedCells);
	}

	private Collection<Point> getResultFromEraser(Field field, Cell lastStepCell) {
		int[][] convertedField = FieldConverter.toEraserField(field);
		eraser.setField(convertedField);
		eraser.setLastStepPoint(lastStepCell.getRow(), lastStepCell.getCol());

		return eraser.getErasedCells();
	}

	private Collection<Cell> convertToCellCollection(Field field, Collection<Point> erasedCells) {
		Collection<Cell> result = new ArrayList<Cell>(erasedCells.size());
		for (Point p : erasedCells) {
			Cell cell = field.getCell(p.getY(), p.getX());
			result.add(cell);
		}

		return result;
	}
}
