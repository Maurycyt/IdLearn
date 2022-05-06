package mimuw.idlearn.userdata;

public class NotEnoughException extends Exception {
	public NotEnoughException() {
		super("Not enough points!");
	}
}
