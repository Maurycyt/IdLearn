package mimuw.idlearn.scoring;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class PointsGiver {
	private static final long POINTS_PER_TICK = 10;
	private static final Timer givingTimer = new Timer();
	private static final Map<String, PointsTimerTask> givingTasks = new HashMap<>();

	private static class PointsTimerTask extends TimerTask {
		long timeMillis;
		public PointsTimerTask(long time) {
			super();
			timeMillis = time;
		}

		@Override
		public void run() {
			PointsManager.addPoints(POINTS_PER_TICK);
		}

		public void start() {
			givingTimer.scheduleAtFixedRate(this, timeMillis, timeMillis);
		}
	}

	public static void setSolutionSpeed(String problem, long timeInMillis) {

		PointsTimerTask task = givingTasks.get(problem);

		if (task != null) {
			task.cancel();
		}

		task = new PointsTimerTask(timeInMillis);
		task.start();
		givingTasks.put(problem, task);
	}

	public static void resetSolution(String problem) {
		PointsTimerTask task = givingTasks.get(problem);
		if (task != null) {
			task.cancel();
		}
	}
}
