package mimuw.idlearn.scoring;

import mimuw.idlearn.userdata.DataManager;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Semaphore;

public class PointsGiver {
	private static final Timer givingTimer = new Timer();
	private static final Map<String, PointsTimerTask> givingTasks = new HashMap<>();
	private static final Map<String, Long> timeStamps = new HashMap<>();
	private static final Semaphore mutex = new Semaphore(1);

	private static class PointsTimerTask extends TimerTask {
		long timeMillis;
		long points;
		public PointsTimerTask(long time, long pointsPerGiving) {
			super();
			timeMillis = time;
			points = pointsPerGiving;
		}

		@Override
		public void run() {
			DataManager.addPoints(points);
		}

		public void start() {
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

		task = new PointsTimerTask(timeInMillis, pointsPerGiving);
		task.start();
		givingTasks.put(problem, task);
		timeStamps.put(problem, timeStamp);


		mutex.release();
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
		HashMap<String, DataManager.PointsGiving> speeds = DataManager.getPointsGiving();
		Date date = new Date();

		for (Map.Entry<String, DataManager.PointsGiving> entry : speeds.entrySet()) {
			PointsTimerTask task = new PointsTimerTask(entry.getValue().timeInterval, entry.getValue().points);
			task.start();
			givingTasks.put(entry.getKey(), task);
			timeStamps.put(entry.getKey(), date.getTime());
		}
	}
}
