package mimuw.idlearn.userdata;

import mimuw.idlearn.idlang.logic.base.ResourceCounter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PerkManager {

	private static Map<String, Integer> perks;
	private static Set<String> PERK_NAMES = new HashSet<>(Collections.singleton("Memory"));

	private static void implementPerk(String perkName) {
		switch (perkName) {
			case "Memory":
				ResourceCounter.MAX_MEMORY = 100_000 * (1 + perks.get("Memory"));
				break;
			default:
				throw new RuntimeException("Invalid perk name");
		}
	}

	public static void setPerkLevel(String name, int level) {
		perks.put(name, level);
		implementPerk(name);
	}
	public static void upgradePerk(String name) {
		int perkLevel = perks.get(name);
		perks.put(name, perkLevel + 1);
		implementPerk(name);
	}


	public static void init() {
		perks = DataManager.getPerks();
		for (String perkName : perks.keySet()) {
			implementPerk(perkName);
		}
	}

	public static Set<String> getPerkNames() {
		return PERK_NAMES;
	}
}
