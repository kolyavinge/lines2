package lines2.model;

import java.util.Iterator;

public class LevelIterator implements Iterator<Level> {

	private int levelNumber = 0;

	public boolean hasNext() {
		return levelNumber < LevelFactory.getLevelscount();
	}

	public Level next() {
		LevelFactory factory = LevelFactory.getInstance();
		Level level = factory.getLevel(levelNumber);
		levelNumber++;

		return level;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}
