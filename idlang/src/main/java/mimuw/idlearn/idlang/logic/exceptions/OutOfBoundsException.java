package mimuw.idlearn.idlang.logic.exceptions;

public class OutOfBoundsException extends SimulationException {
	public OutOfBoundsException(long index, long size) {
		super("Index " + index + " out of bounds for array of size " + size + ".");
	}
}
