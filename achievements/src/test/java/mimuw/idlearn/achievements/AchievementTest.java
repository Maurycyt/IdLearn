package mimuw.idlearn.achievements;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.*;

public class AchievementTest {
	@Test
	public void testAchievementManager() {
		AchievementManager.init(Map.ofEntries(
				Map.entry(AchievementManager.CustomTaskLoaded, 1),
				Map.entry(AchievementManager.TasksCompleted, 4)
		));

		assertEquals(
				Set.of(
						"TasksCompleted",
						"CodeBlocksPlaced",
						"ExpectedTimeBeaten",
						"ExpectedMemoryBeaten",
						"CustomTaskLoaded"),
				AchievementManager.getAchievementNames()
		);

		assertEquals(
				List.of(),
				AchievementManager.getAchievementDialogsToShow()
		);

		assertEquals(1, AchievementManager.get(AchievementManager.CustomTaskLoaded).getProgress());
		assertEquals(4, AchievementManager.get(AchievementManager.TasksCompleted).getProgress());
		assertEquals(1, AchievementManager.get(AchievementManager.CustomTaskLoaded).getUnlockedLevel());
		assertEquals(2, AchievementManager.get(AchievementManager.TasksCompleted).getUnlockedLevel());
		assertEquals(0, AchievementManager.get(AchievementManager.CodeBlocksPlaced).getUnlockedLevel());

		AchievementManager.get(AchievementManager.CodeBlocksPlaced).increaseProgress();
		AchievementManager.get(AchievementManager.CodeBlocksPlaced).increaseProgress();

		assertEquals(2, AchievementManager.get(AchievementManager.CodeBlocksPlaced).getProgress());
		assertEquals(1000 , AchievementManager.get(AchievementManager.CodeBlocksPlaced).getMaxProgress());
		assertEquals(2, AchievementManager.get(AchievementManager.CodeBlocksPlaced).getProgress());
		assertEquals(1, AchievementManager.get(AchievementManager.CodeBlocksPlaced).getUnlockedLevel());
	}
}
