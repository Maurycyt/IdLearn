package mimuw.idlearn.scoring;

import mimuw.idlearn.core.Emitter;

public class PointsManager {
	static private long points = 0;
	static public Emitter pointsChangeEmitter = new Emitter();

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
}
