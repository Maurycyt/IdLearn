package mimuw.idlearn.idlang.logic.exceptions;

public class EndOfInputException extends SimulationException {
	public EndOfInputException() {
		super("Tried to read past EOF.");
	}
}
