package mimuw.idlearn.userdata;

import mimuw.idlearn.achievements.AchievementManager;
import mimuw.idlearn.achievements.AchievementProgressEvent;
import mimuw.idlearn.core.Emitter;
import mimuw.idlearn.core.Event;
import mimuw.idlearn.core.Listener;
import mimuw.idlearn.packages.PackageManager;
import mimuw.idlearn.properties.Config;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class DataManager {

	private static final File saveFile = Path.of(Config.getDataPath().toString(), "savefile/user.savedata").toFile();
	private static final long AUTOSAVE_INTERVAL = 30000;
	private static Timer autosaveTimer;
	private static final String[] STARTING_TASKS = {"Addition"};
	private static Data data = new Data();
	private static final Emitter pointsChangeEmitter = new Emitter();
	private static final Listener achievementsListener = event -> {
		if (event.type() == AchievementProgressEvent.class){
			AchievementProgressEvent value = (AchievementProgressEvent)event.value();
			data.achievements.put(value.name(), value.progress());
			try {
				saveData();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	};

	public static class PointsGiving {
		public long timeInterval;
		public long points;
	}
	private static class Data {
		public long points = 0;
		public List<String> unlockedTasks = new ArrayList<>(List.of(STARTING_TASKS));
		public Map<String, PointsGiving> pointsGiving = new HashMap<>();
		public Map<String, Integer> perks = new HashMap<>();
		public Map<String, Integer> achievements = new HashMap<>();
		public Map<String, CodeData> userCode = new HashMap<>();
		public Map<String, Integer> testIDs = new HashMap<>();
		public Data() {
			for (String perk : PerkManager.getPerkNames()) {
				perks.put(perk, 0);
			}
			for (String achievement : AchievementManager.getAchievementNames()) {
				achievements.put(achievement, 0);
			}
		}
	}

	// Points
	public static long showPoints() {
		return data.points;
	}

	public static void payPoints(long amount) throws NotEnoughPointsException {
		if (amount > data.points) {
			throw new NotEnoughPointsException(amount);
		}
		data.points -= amount;
		pointsChangeEmitter.fire(data.points);
	}

	public static void addPoints(long amount) {
		data.points += amount;
		pointsChangeEmitter.fire(data.points);
	}

	public static void setPoints(long amount) {
		data.points = amount;
		pointsChangeEmitter.fire(data.points);
	}

	public static void connectToPoints(Listener listener) {
		pointsChangeEmitter.connect(listener);
	}


	// Perks
	public static Integer getLevel(String perkName) {
		return data.perks.get(perkName);
	}

	// TestIDs
	public static int getTestID(String problem) {
		if (data.testIDs.containsKey(problem)) {
			return data.testIDs.get(problem);
		}
		return 1;
	}

	public static void incrementTestID(String problem) throws IOException {
		if (data.testIDs.containsKey(problem)) {
			int old = data.testIDs.get(problem);
			data.testIDs.put(problem, old + 1);
		}
		else {
			data.testIDs.put(problem, 2);
		}
		saveData();
	}

	// Tasks
	public static void unlockTask(String task) throws IOException {
		data.unlockedTasks.add(task);
		saveData();
	}
	public static List<String> getUnlockedTasks() {
		return Collections.unmodifiableList(data.unlockedTasks);
	}
	public static void resetUnlockedTasks() throws IOException {
		data.unlockedTasks.clear();
		saveData();
	}

	// Points giving data
	public static void setGiverData(String task, long time, long points) throws IOException {
		PointsGiving pg = new PointsGiving();
		pg.timeInterval = time;
		pg.points = points;
		data.pointsGiving.put(task, pg);

		// Notify achievement manager of any new achievements.
		AchievementManager.get(AchievementManager.TasksCompleted).setProgress(data.pointsGiving.size());

		saveData();
	}
	public static Map<String, PointsGiving> getPointsGiving() {return new HashMap<>(data.pointsGiving);}

	public static Map<String, Integer> getPerks() {return data.perks;}
	public static Map<String, Integer> getAchievements() {return data.achievements;}

	// User code
	public static void updateUserCode(String task, CodeData codeData) throws IOException {
		data.userCode.put(task, codeData);
		saveData();
	}
	public static CodeData getUserCode(String task) {
		return data.userCode.getOrDefault(task, null);
	}

	public static void saveData() throws IOException {
		System.out.println("Saving to file: " + saveFile.getAbsolutePath());
		if (!saveFile.isFile()) {
			saveFile.getParentFile().mkdirs();
			saveFile.createNewFile();
		}

		(new Yaml()).dump(data, new FileWriter(saveFile));
	}
	private static void loadData() throws IOException {
		System.out.println("Loading from file: " + saveFile.getAbsolutePath());
		if (!saveFile.isFile()) {
			return;
		}

		data = (new Yaml(new Constructor(Data.class))).load(Files.readString(Path.of(saveFile.toString())));
	}

	public static void resetData() throws IOException {
		data = new Data();
		saveData();
	}

	private static void setupAutosaveTimer() {
		autosaveTimer = new Timer();
		TimerTask autosave = new TimerTask() {
			@Override
			public void run() {
				try {
					saveData();
				}
				catch (IOException e) {
					System.out.println("FAILED TO AUTO-SAVE!");
					throw new Error("Failed auto-save");
				}
			}
		};
		autosaveTimer.scheduleAtFixedRate(autosave, AUTOSAVE_INTERVAL, AUTOSAVE_INTERVAL);
	}

	private static void stopAutosaveTimer() {
		autosaveTimer.cancel();
	}

	public static void init() throws IOException {
		loadData();
		AchievementManager.init(getAchievements());
		AchievementManager.emitter.connect(achievementsListener);
		setupAutosaveTimer();
	}

	public static void exit() throws IOException {
		saveData();
		stopAutosaveTimer();
	}
}
