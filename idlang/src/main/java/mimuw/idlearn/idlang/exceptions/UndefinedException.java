package mimuw.idlearn.idlang.exceptions;

public class UndefinedException extends SimulationException {
	public UndefinedException(String name) {
		super("Variable '" + name + "' not defined.");
	}
}
