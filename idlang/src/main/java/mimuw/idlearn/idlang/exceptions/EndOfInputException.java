package mimuw.idlearn.idlang.exceptions;

public class EndOfInputException extends SimulationException {
	public EndOfInputException() {
		super("Tried to read past EOF.");
	}
}
