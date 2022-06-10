package mimuw.idlearn.scoring;

import mimuw.idlearn.core.Emitter;
import mimuw.idlearn.core.Listener;
import mimuw.idlearn.userdata.DataManager;
import mimuw.idlearn.userdata.PerkManager;

import java.io.IOException;
import javafx.application.Platform;
import java.util.*;
import java.util.concurrent.Semaphore;

public class PointsGiver {
	private static final Timer givingTimer = new Timer();

	private static double speedMultiplier = 1.0;
	private static final Map<String, PointsTimerTask> givingTasks = new HashMap<>();
	private static final Map<String, Long> timeStamps = new HashMap<>();
	private static final Map<String, Long> startTime = new HashMap<>();
	private static final Semaphore mutex = new Semaphore(1);
	private static final Emitter taskCompletionEmitter = new Emitter();

	public static Set<String> getCompletedTasks() {
		return givingTasks.keySet();
	}

	public static void connectToTaskCompletion(Listener listener) {
		taskCompletionEmitter.connect(listener);
	}

	private static class PointsTimerTask extends TimerTask {
		long timeMillis;
		long points;
		String problem;
		public PointsTimerTask(long time, long pointsPerGiving, String problem) {
			super();
			timeMillis = time;
			points = pointsPerGiving;
			this.problem = problem;
		}

		@Override
		public void run() {
			Platform.runLater(() -> DataManager.addPoints(points));
		}

		public void start() {
			Date date = new Date();
			startTime.put(problem, date.getTime());
			givingTimer.scheduleAtFixedRate(this, timeMillis, timeMillis);
		}
	}

	public static void setSolutionSpeed(String problem, long timeInMillis, long pointsPerGiving) throws IOException {
		Date date = new Date();
		setSolutionSpeed(problem, timeInMillis, date.getTime(), pointsPerGiving);
	}

	public static void setSolutionSpeed(String problem, long timeInMillis, long timeStamp, long pointsPerGiving) throws IOException {

		mutex.acquireUninterruptibly();

		Long oldStamp = timeStamps.get(problem);
		if (oldStamp != null && oldStamp > timeStamp) {
			mutex.release();
			return;
		}

		DataManager.setGiverData(problem, timeInMillis, pointsPerGiving);
		PointsTimerTask task = givingTasks.get(problem);

		if (task != null) {
			task.cancel();
		}

		task = new PointsTimerTask((long)(timeInMillis / speedMultiplier), pointsPerGiving, problem);
		task.start();
		givingTasks.put(problem, task);
		timeStamps.put(problem, timeStamp);


		mutex.release();

		taskCompletionEmitter.fire(problem);
	}

	public static long getSolutionRealSpeed(String problem) {
		long interval = DataManager.getPointsGiving().get(problem).timeInterval;
		return (long)(interval / speedMultiplier);
	}
	public static long getOffset(String problem) {
		Date date = new Date();
		long realSpeed = getSolutionRealSpeed(problem);
		long start = startTime.get(problem);
		return (date.getTime() - start) % realSpeed;
	}

	public static void resetSolution(String problem) {

		mutex.acquireUninterruptibly();

		PointsTimerTask task = givingTasks.get(problem);
		if (task != null) {
			task.cancel();
		}
		givingTasks.remove(problem);
		timeStamps.remove(problem);

		mutex.release();
	}

	public static void resetSolutions() {

		mutex.acquireUninterruptibly();

		for (Map.Entry<String, PointsTimerTask> entry : givingTasks.entrySet()) {
			entry.getValue().cancel();
		}
		timeStamps.clear();
		givingTasks.clear();

		mutex.release();
	}

	public static void exit() {
		givingTimer.cancel();
	}

	public static void loadSpeeds() {
		// Make it so that PerkManager informs PointsGiver of new speed.
		PerkManager.connectToOnUpgradeEmitter("Speed", event -> {
			if (event.value() instanceof Double) {
				setSpeedMultiplier((Double)event.value());
			}
		});

		// Prepare the usual stuff.
		Map<String, DataManager.PointsGiving> speeds = DataManager.getPointsGiving();
		Date date = new Date();

		for (Map.Entry<String, DataManager.PointsGiving> entry : speeds.entrySet()) {
			PointsTimerTask task = new PointsTimerTask(entry.getValue().timeInterval, entry.getValue().points, entry.getKey());
			task.start();
			givingTasks.put(entry.getKey(), task);
			timeStamps.put(entry.getKey(), date.getTime());
		}

		// Force PerkManager to inform of speed, which was read from user data.
		PerkManager.refreshPerk("Speed");
	}

	public static void reloadSpeeds() {
		Map<String, DataManager.PointsGiving> speeds = DataManager.getPointsGiving();

		for (Map.Entry<String, DataManager.PointsGiving> entry : speeds.entrySet()) {
			try {
				setSolutionSpeed(entry.getKey(), entry.getValue().timeInterval, entry.getValue().points);
			} catch (IOException e) {
				e.printStackTrace();
				// TODO: clean this up.
			}
		}
	}

	public static void setSpeedMultiplier(double newSpeedMultiplier) {
		speedMultiplier = newSpeedMultiplier;
		resetSolutions();
		reloadSpeeds();
	}
}
