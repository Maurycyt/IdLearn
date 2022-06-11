package mimuw.idlearn.achievements;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

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
	private final Deque<String> toShow = new ArrayDeque<>();

	public String getDisplayedText() {
		return levels[unlockedLevel].displayText();
	}

	private void updateLevel() {
		while (unlockedLevel < levels.length && progress >= levels[unlockedLevel].threshold()) {
			AchievementManager.emitter.fire(new AchievementProgressEvent(name, progress));
			popup(levels[unlockedLevel].displayText());
			unlockedLevel++;
		}
	}

	private void popup(String text) {
		toShow.add(text);
	}

	public Optional<String> getNextPopup() {
		return Optional.ofNullable(toShow.poll());
	}

	public void setProgress(int progress) {
		this.progress = progress;
		updateLevel();
	}

	public void increaseProgress() {
		progress++;
		updateLevel();
	}

	void load(int progress) {
		this.progress = progress;
		while(unlockedLevel < levels.length && progress >= levels[unlockedLevel].threshold())
			unlockedLevel++;
	}
}
