package mimuw.idlearn.userdata;

public class NotEnoughPointsException extends Exception {
	public long triedToPay;
	public NotEnoughPointsException(long triedToPay) {
		super("Not enough points!");
		this.triedToPay = triedToPay;
	}
}
