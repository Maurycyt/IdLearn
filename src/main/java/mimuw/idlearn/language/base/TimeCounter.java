package mimuw.idlearn.language.base;

public class TimeCounter {
	private final double MAX_TIME = 10000000;
	private double time = 0;
	public TimeCounter() {}
	public double addTime(double time) throws RuntimeException {
		this.time += time;
		if (this.time > MAX_TIME) {
			throw new RuntimeException("Time exceeded");
		}
		return this.time;
	}
	public double getTime() {
		return time;
	}
}
