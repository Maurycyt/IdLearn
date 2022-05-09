package mimuw.idlearn.userdata;

import mimuw.idlearn.core.Emitter;
import mimuw.idlearn.core.Listener;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class DataManager {

	private static final File saveFile = Path.of(System.getProperty("user.home"), ".idlearn", "savefile/user.savedata").toFile();
	private static final long AUTOSAVE_INTERVAL = 30000;
	private static final Timer autosaveTimer = new Timer();

	private static class Data {
		public long points = 0;
		public ArrayList<String> unlockedTasks = new ArrayList<>();
		public HashMap<String, Long> solutionSpeed = new HashMap<>();
	}

	private static Data data = new Data();

	private static final Emitter pointsChangeEmitter = new Emitter();

	// Points

	public static long showPoints() {
		return data.points;
	}

	public static void payPoints(long amount) throws NotEnoughPointsException {
		if (amount > data.points) {
			throw new NotEnoughPointsException();
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


	// Tasks
	public static void unlockTask(String task) throws IOException {
		data.unlockedTasks.add(task);
		saveData();
	}
	public static ArrayList<String> getUnlockedTasks() {
		return new ArrayList<>(data.unlockedTasks);
	}
	public static void resetUnlockedTasks() throws IOException {
		data.unlockedTasks.clear();
		saveData();
	}

	// Speeds
	public static void setSpeed(String task, long time) throws IOException {
		data.solutionSpeed.put(task, time);
		saveData();
	}
	public static HashMap<String, Long> getSpeeds() {return new HashMap<>(data.solutionSpeed);}

	private static void saveData() throws IOException {
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
		data.points = 0;
		data.unlockedTasks = new ArrayList<>();
		data.solutionSpeed = new HashMap<>();
		saveData();
	}

	private static void setupAutosaveTimer() {
		TimerTask autosave = new TimerTask() {
			@Override
			public void run() {
				try {
					saveData();
				}
				catch (IOException e) {
					System.out.println("FAILED TO AUTOSAVE!");
					throw new Error("Failed autosave");
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
		setupAutosaveTimer();
	}

	public static void exit() throws IOException {
		saveData();
		stopAutosaveTimer();
	}
}