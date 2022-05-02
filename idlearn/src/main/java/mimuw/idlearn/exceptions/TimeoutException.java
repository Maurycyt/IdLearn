package mimuw.idlearn.language.exceptions;

public class TimeoutException extends SimulationException {
	public TimeoutException(double limit) {
		super("Time limit of " + limit + " exceeded.");
	}
}
