package mimuw.idlearn.userdata;

import mimuw.idlearn.core.Emitter;
import mimuw.idlearn.core.Listener;

import java.io.Serializable;
import java.util.ArrayList;

public class DataManager {
	static final const long AUTOSAVE_TIMER = 30000;

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
	public static void unlockTask(String task) {
		unlockedTasks.add(task);
		saveData();
	}
	public static ArrayList<String> getUnlockedTasks() {
		return new ArrayList<>(unlockedTasks);
	}

	private static class Data implements Serializable {
		long points;
		ArrayList<String> unlockedTasks;
	}
	private static void saveData() {

	}
	private static void loadData() {

	}

	private static void init() {
		loadData();
		setupAutosaveTimer();
	}
}
