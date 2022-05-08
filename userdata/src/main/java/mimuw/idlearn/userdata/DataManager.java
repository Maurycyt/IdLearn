package mimuw.idlearn.userdata;

import mimuw.idlearn.core.Emitter;
import mimuw.idlearn.core.Listener;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DataManager {

	private static final File saveFile = new File("../savefile/user.savedata");
	private static final long AUTOSAVE_TIMER = 30000;
	private static final Timer autosave_timer = new Timer();

	private static long points = 0;
	private static ArrayList<String> unlockedTasks = new ArrayList<>();
	private static final Emitter pointsChangeEmitter = new Emitter();

	// Points

	public static long showPoints() {
		return points;
	}

	public static void payPoints(long amount) throws NotEnoughPointsException {
		if (amount > points) {
			throw new NotEnoughPointsException();
		}
		points -= amount;
		pointsChangeEmitter.fire(points);
	}

	public static void addPoints(long amount) {
		points += amount;
		pointsChangeEmitter.fire(points);
	}

	public static void setPoints(long amount) {
		points = amount;
		pointsChangeEmitter.fire(points);
	}

	public static void connectToPoints(Listener listener) {
		pointsChangeEmitter.connect(listener);
	}


	// Tasks
	public static void unlockTask(String task) throws IOException {
		unlockedTasks.add(task);
		saveData();
	}
	public static ArrayList<String> getUnlockedTasks() {
		return new ArrayList<>(unlockedTasks);
	}
	public static void resetUnlockedTasks() throws IOException {
		unlockedTasks.clear();
		saveData();
	}

	public static class Data {
		public long points;
		public ArrayList<String> unlockedTasks;
	}
	private static void saveData() throws IOException {
		System.out.println("Using file: " + saveFile.getAbsolutePath());
		if (!saveFile.isFile()) {
			File directory = saveFile.getParentFile();
			if (!directory.exists()) {
				directory.mkdir();
			}
			saveFile.createNewFile();
		}

		Gson gson = new Gson();

		Data data = new Data();
		data.points = points;
		data.unlockedTasks = unlockedTasks;

		try (FileWriter writer = new FileWriter(saveFile)) {
			gson.toJson(data, writer);
		}

	}
	private static void loadData() throws IOException {
		Gson gson = new Gson();

		System.out.println("Using file: " + saveFile.getAbsolutePath());
		if (!(saveFile.isFile())) {
			return;
		}

		try (JsonReader reader = new JsonReader(new FileReader(saveFile))) {
			Data data = gson.fromJson(reader, Data.class);
			System.out.println(data);
			points = data.points;
			unlockedTasks = data.unlockedTasks;
		}
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
		autosave_timer.scheduleAtFixedRate(autosave, AUTOSAVE_TIMER, AUTOSAVE_TIMER);
	}

	public static void init() throws IOException {
		loadData();
		setupAutosaveTimer();
	}
}
