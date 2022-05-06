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

	private static final String SAVE_LOCATION = "/user.savedata";
	private static final long AUTOSAVE_TIMER = 30000;
	private static final Timer autosave_timer = new Timer();

	// Points
	private static long points = 0;
	private static final Emitter pointsChangeEmitter = new Emitter();

	public static long showPoints() {
		return points;
	}

	public static void payPoints(long amount) throws NotEnoughException {
		if (amount > points) {
			throw new NotEnoughException();
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
	private static ArrayList<String> unlockedTasks;
	public static void unlockTask(String task) throws IOException {
		unlockedTasks.add(task);
		saveData();
	}
	public static ArrayList<String> getUnlockedTasks() {
		return new ArrayList<>(unlockedTasks);
	}

	private static class Data {
		long points;
		ArrayList<String> unlockedTasks;
	}
	private static void saveData() throws IOException {


		File saveFile = new File(SAVE_LOCATION);

		Gson gson = new Gson();

		Data data = new Data();
		data.points = points;
		data.unlockedTasks = unlockedTasks;

		FileWriter writer = new FileWriter(SAVE_LOCATION);
		gson.toJson(data, writer);

	}
	private static void loadData() throws IOException {
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new FileReader(SAVE_LOCATION));
		Data data = gson.fromJson(reader, Data.class);
		points = data.points;
		unlockedTasks = data.unlockedTasks;
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
