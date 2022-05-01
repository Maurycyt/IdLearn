package mimuw.idlearn.language.exceptions;

public class UndefinedException extends SimulationException {
	public UndefinedException(String name) {
		super("Variable '" + name + "' not defined.");
	}
}
