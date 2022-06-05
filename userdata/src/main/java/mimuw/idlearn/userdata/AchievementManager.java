package mimuw.idlearn.userdata;

import mimuw.idlearn.core.Listener;
import mimuw.idlearn.packages.PackageManager;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AchievementManager {
	private static Map<String, Integer> unlockedAchievementLevels;
	private static final Map<String, Integer> maxAchievementLevels;
	private static final Map<String, Listener> achievementListeners;

	public static final String TasksCompleted = "TasksCompleted";
	public static final String CodeBlocksPlaced = "CodeBlocksPlaced";
	public static final String ExpectedTimeBeaten = "ExpectedTimeBeaten";
	public static final String ExpectedMemoryBeaten = "ExpectedMemoryBeaten";
	public static final String CustomTaskLoaded = "CustomTaskLoaded";
	
	static {
		maxAchievementLevels = new HashMap<>(Map.ofEntries(
				new AbstractMap.SimpleEntry<>(TasksCompleted, 3),
				new AbstractMap.SimpleEntry<>(CodeBlocksPlaced, 3),
				new AbstractMap.SimpleEntry<>(ExpectedTimeBeaten, 1),
				new AbstractMap.SimpleEntry<>(ExpectedMemoryBeaten, 1),
				new AbstractMap.SimpleEntry<>(CustomTaskLoaded, 1)
		));
		achievementListeners = new HashMap<>(Map.ofEntries(
				new AbstractMap.SimpleEntry<>(CustomTaskLoaded, event -> unlockAchievementLevel(CustomTaskLoaded, 1))
		));
	}

	public static void init() {
		unlockedAchievementLevels = DataManager.getAchievements();
		PackageManager.customTaskEmitter.connect(achievementListeners.get(CustomTaskLoaded));
	}

	public static Set<String> getAchievementNames() {
		return maxAchievementLevels.keySet();
	}

	public static void unlockAchievementLevel(String achievementName, int level) {
		int maxLevel = maxAchievementLevels.get(achievementName);
		int currentLevel = unlockedAchievementLevels.get(achievementName);
		level = Math.min(level, maxLevel);
		level = Math.max(level, currentLevel);
		if (level != unlockedAchievementLevels.get(achievementName)) {
			System.out.println("Unlocked achievement " + achievementName + " on level " + level + "!");
		}
		unlockedAchievementLevels.put(achievementName, level);
		try {
			DataManager.saveData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Integer getLevel(String achievementName) {
		return unlockedAchievementLevels.get(achievementName);
	}

	public static Integer getMaxLevel(String achievementName) {
		return maxAchievementLevels.get(achievementName);
	}
}
