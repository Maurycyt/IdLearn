package mimuw.idlearn.idlang.logic.base;

import mimuw.idlearn.idlang.logic.exceptions.MemoryException;
import mimuw.idlearn.idlang.logic.exceptions.TimeoutException;

public class ResourceCounter {
	public final static double MAX_TIME = 100_000_000;
	public static int MAX_MEMORY = 100_000;
	private double time = 0;
	private int memory = 0;
	public ResourceCounter() {}
	public double addTime(double time) throws TimeoutException {
		this.time += time;
		if (this.time > MAX_TIME) {
			throw new TimeoutException(MAX_TIME);
		}
		return this.time;
	}
	public int addMemory(int memory) throws MemoryException {
		this.memory += memory;
		if (this.memory > MAX_MEMORY) {
			throw new MemoryException(MAX_MEMORY);
		}
		return this.memory;
	}
	public double getTime() {
		return time;
	}

	public void clear() {
		time = 0;
		memory = 0;
	}
}
