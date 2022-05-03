package mimuw.idlearn.scoring;

public class NotEnoughException extends Exception {
	public NotEnoughException() {
		super("Not enough points!");
	}
}
