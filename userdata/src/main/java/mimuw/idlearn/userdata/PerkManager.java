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
	private static final Map<String, Consumer<Integer>> onUpgrade;
	private static final Map<String, Emitter> onUpgradeEmitters;
	private static final Emitter perkUnlockingEmitter = new Emitter();
	private static final int BASE_MEMORY = 100_000;

	private static final String Memory = "Memory";
	private static final String Speed = "Speed";


	static {
		maxPerkLevels = new HashMap<>(Map.ofEntries(
				new AbstractMap.SimpleEntry<>(Memory, 10),
				new AbstractMap.SimpleEntry<>(Speed, 4)
		));
		onUpgradeEmitters = new HashMap<>(Map.ofEntries(
				new AbstractMap.SimpleEntry<>(Memory, new Emitter()),
				new AbstractMap.SimpleEntry<>(Speed, new Emitter())
		));
		onUpgrade = new HashMap<>(Map.ofEntries(
				new AbstractMap.SimpleEntry<>(Memory, level ->
						ResourceCounter.MAX_MEMORY = BASE_MEMORY * (1 + level)
				),
				new AbstractMap.SimpleEntry<>(Speed, level ->
						onUpgradeEmitters.get(Speed).fire((double)(1 + level))
				)
		));
	}
	
	public static void setAndApplyPerkLevel(String perkName, int level) {
		unlockedPerkLevels.put(perkName, level);
		onUpgrade.get(perkName).accept(level);
		perkUnlockingEmitter.fire(new AbstractMap.SimpleEntry<>(perkName, level));
	}

	public static void upgradePerk(String perkName) throws IOException, NotEnoughPointsException, ReachedMaxLevelException {
		if (getLevel(perkName).equals(getMaxLevel(perkName))) {
			throw new ReachedMaxLevelException();
		}
		DataManager.payPoints(100); //TODO: replace with actual cost
		int newLevel = 1 + unlockedPerkLevels.get(perkName);
		assert newLevel <= maxPerkLevels.get(perkName);

		setAndApplyPerkLevel(perkName, newLevel);
		DataManager.saveData();
	}

	public static void init() {
		unlockedPerkLevels = DataManager.getPerks();
		for (String perkName : unlockedPerkLevels.keySet()) {
			setAndApplyPerkLevel(perkName, DataManager.getLevel(perkName));
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

	public static void connectToOnUpgradeEmitter(String perkName, Listener listener) {
		onUpgradeEmitters.get(perkName).connect(listener);
	}

	public static void refreshPerk(String perkName) {
		onUpgrade.get(perkName).accept(unlockedPerkLevels.get(perkName));
	}
}
