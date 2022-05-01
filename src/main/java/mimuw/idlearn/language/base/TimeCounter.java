package mimuw.idlearn.language.base;

import mimuw.idlearn.language.exceptions.TimeoutException;

public class TimeCounter {
	public final static double MAX_TIME = 100_000_000;
	private double time = 0;
	public TimeCounter() {}
	public double addTime(double time) throws TimeoutException {
		this.time += time;
		if (this.time > MAX_TIME) {
			throw new TimeoutException(MAX_TIME);
		}
		return this.time;
	}
	public double getTime() {
		return time;
	}
}
