package mimuw.idlearn.userdata;

import mimuw.idlearn.core.Emitter;
import mimuw.idlearn.core.Listener;
import mimuw.idlearn.idlang.logic.base.ResourceCounter;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

public class PerkManager {
	private static Map<String, Integer> unlockedPerkLevels;
	private static final Map<String, Integer> maxPerkLevels;
	private static final Map<String, Consumer<Integer>> unlockingBehaviors;
	private static final Emitter perkUnlockingEmitter = new Emitter();

	static {
		maxPerkLevels = new HashMap<>(Map.ofEntries(
				new AbstractMap.SimpleEntry<>("Memory", 1)
		));
		unlockingBehaviors = new HashMap<>(Map.ofEntries(
				new AbstractMap.SimpleEntry<>("Memory", level ->
						ResourceCounter.MAX_MEMORY = 100_000 * (1 + level)
				)
		));
	}
	
	public static void setAndApplyPerkLevel(String perkName, int level) {
		unlockedPerkLevels.put(perkName, level);
		unlockingBehaviors.get(perkName).accept(level);
		perkUnlockingEmitter.fire(new AbstractMap.SimpleEntry<>(perkName, level));
	}

	public static void upgradePerk(String perkName) throws IOException {
		int newLevel = 1 + unlockedPerkLevels.get(perkName);
		assert newLevel <= maxPerkLevels.get(perkName);

		setAndApplyPerkLevel(perkName, newLevel);
		DataManager.saveData();
	}

	public static void init() {
		unlockedPerkLevels = DataManager.getPerks();
		for (String perkName : unlockedPerkLevels.keySet()) {
			setAndApplyPerkLevel(perkName, 0);
		}
	}

	public static Set<String> getPerkNames() {
		return maxPerkLevels.keySet();
	}

	public static Integer getLevel(String perkName) {
		return unlockedPerkLevels.get(perkName);
	}

	public static Integer getMaxLevel(String perkName) {
		return maxPerkLevels.get(perkName);
	}

	public static void connectToPerkUnlocking(Listener listener) {
		perkUnlockingEmitter.connect(listener);
	}
}
