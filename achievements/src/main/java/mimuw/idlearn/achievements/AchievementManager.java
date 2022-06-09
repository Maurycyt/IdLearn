package mimuw.idlearn.achievements;


import mimuw.idlearn.core.Emitter;

import java.util.*;

public class AchievementManager {
	private static final Map<String, Achievement> achievements = new HashMap<>();
	public static final String TasksCompleted = "TasksCompleted";
	public static final String CodeBlocksPlaced = "CodeBlocksPlaced";
	public static final String ExpectedTimeBeaten = "ExpectedTimeBeaten";
	public static final String ExpectedMemoryBeaten = "ExpectedMemoryBeaten";
	public static final String CustomTaskLoaded = "CustomTaskLoaded";

	public static Emitter emitter = new Emitter();

	public static void init(Map<String, Integer> achievementProgress) {
		achievements.put(TasksCompleted, new Achievement(TasksCompleted,
				new AchievementLevel("Completed 1 task", 1),
				new AchievementLevel("Completed 3 tasks", 3),
				new AchievementLevel("Completed all tasks", 5)
		));
		achievements.put(CodeBlocksPlaced, new Achievement(CodeBlocksPlaced,
				new AchievementLevel("Placed 2 blocks", 2),
				new AchievementLevel("Placed 20 blocks", 20),
				new AchievementLevel("Placed 300 blocks", 300),
				new AchievementLevel("Placed 1000 blocks", 1000)
		));
		achievements.put(ExpectedTimeBeaten, new Achievement(ExpectedTimeBeaten,
				new AchievementLevel("Expected time beaten", 1)
		));
		achievements.put(ExpectedMemoryBeaten, new Achievement(ExpectedMemoryBeaten,
				new AchievementLevel("Expected memory beaten", 1)
		));
		achievements.put(CustomTaskLoaded, new Achievement(CustomTaskLoaded,
				new AchievementLevel("Custom task loaded", 1)
		));
		achievementProgress.forEach((name, progress) -> achievements.get(name).load(progress));
	}

	public static Set<String> getAchievementNames() {
		return achievements.keySet();
	}

	public static Achievement get(String name) {
		return achievements.get(name);
	}

	public static List<String> getAchievementDialogsToShow() {
		List<String> out = new ArrayList<>();
		achievements.forEach((name, achievement) -> {
			Optional<String> next;
			while ((next = achievement.getNextPopup()).isPresent()) {
				out.add(next.get());
			}
		});
		return out;
	}
}
