package lines2.model;

public class LevelFactory {

	private static final int levelsCount = 3;
	private static LevelFactory instance;

	static {
		instance = new LevelFactory();
	}

	public static LevelFactory getInstance() {
		return instance;
	}

	public static int getLevelscount() {
		return levelsCount;
	}

	private Level[] levels;

	protected LevelFactory() {
		levels = new Level[levelsCount];
		initLevels();
	}

	public Level getLevel(int number) {
		if (0 <= number && number < levelsCount)
			return levels[number];
		else
			throw new IllegalArgumentException(String.format("Max level number is %d", levelsCount - 1));
	}

	private void initLevels() {
		initLevel1();
		initLevel2();
		initLevel3();
	}

	private void initLevel1() {
		Level level = new Level();
		level.setNumber(0);
		level.setBallColorsCount(3);
		RandomFillStrategy fillStrategy = new RandomFillStrategy(3);
		fillStrategy.setFillRange(3, 4);
		level.setFillStrategy(fillStrategy);
		level.setEraseLineLength(4);
		level.setEraseStrategy(new StraightEraseStrategy(4));
		level.setFieldRows(7);
		level.setFieldCols(6);
		level.setTotalLevelScores(2000);

		levels[0] = level;
	}

	private void initLevel2() {
		Level level = new Level();
		level.setNumber(1);
		level.setBallColorsCount(4);
		RandomFillStrategy fillStrategy = new RandomFillStrategy(4);
		fillStrategy.setFillRange(4, 5);
		level.setFillStrategy(fillStrategy);
		level.setEraseLineLength(4);
		level.setEraseStrategy(new StraightEraseStrategy(4));
		level.setFieldRows(7);
		level.setFieldCols(6);
		level.setTotalLevelScores(3000);

		levels[1] = level;
	}
	
	private void initLevel3() {
		Level level = new Level();
		level.setNumber(2);
		level.setBallColorsCount(5);
		RandomFillStrategy fillStrategy = new RandomFillStrategy(5);
		fillStrategy.setFillRange(3, 4);
		level.setFillStrategy(fillStrategy);
		level.setEraseLineLength(4);
		level.setEraseStrategy(new StraightEraseStrategy(4));
		level.setFieldRows(7);
		level.setFieldCols(6);
		level.setTotalLevelScores(4000);

		levels[2] = level;
	}
}
