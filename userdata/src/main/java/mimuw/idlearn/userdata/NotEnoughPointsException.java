package mimuw.idlearn.userdata;

public class NotEnoughPointsException extends Exception {
	public final long triedToPay;
	public NotEnoughPointsException(long triedToPay) {
		super("Not enough points!");
		this.triedToPay = triedToPay;
	}
}
