package mimuw.idlearn.userdata;

public class NotEnoughPointsException extends Exception {
	public NotEnoughPointsException() {
		super("Not enough points!");
	}
}
