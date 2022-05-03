package mimuw.idlearn.idlang.exceptions;

public class MemoryException extends SimulationException {
	public MemoryException(int limit) {
		super("Memory limit of " + limit + " exceded.");
	}
}
