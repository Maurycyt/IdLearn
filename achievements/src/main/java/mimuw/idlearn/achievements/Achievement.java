package mimuw.idlearn.achievements;

public class Achievement {
	public Achievement(String name, AchievementLevel... levels) {
		unlockedLevel = 0;
		progress = 0;
		this.levels = levels;
		this.name = name;
	}

	private final String name;
	private int unlockedLevel;
	private int progress;
	private final AchievementLevel[] levels;

	public String getDisplayedText() {
		return levels[unlockedLevel].displayText();
	}

	private void updateLevel() {
		while (unlockedLevel + 1 < levels.length && levels[unlockedLevel + 1].threshold() > progress) {
			unlockedLevel++;
			popup(levels[unlockedLevel].displayText());
		}
	}

	public void popup(String text) {
		// TODO
	}

	public void setProgress(int progress) {
		this.progress = progress;
		updateLevel();
		AchievementManager.emitter.fire(new AchievementProgressEvent(name, progress));
	}

	public void increaseProgress() {
		progress++;
		updateLevel();
	}

	public void unlockNextLevel() {
		if (unlockedLevel + 1 < levels.length)
			setProgress(levels[unlockedLevel + 1].threshold());
	}

	void load(int progress) {
		this.progress = progress;
		while(unlockedLevel + 1 < levels.length && levels[unlockedLevel + 1].threshold() > progress)
			unlockedLevel++;
	}
}
