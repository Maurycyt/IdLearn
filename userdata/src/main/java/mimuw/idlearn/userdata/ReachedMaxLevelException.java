package mimuw.idlearn.userdata;

public class ReachedMaxLevelException extends Exception {
	public ReachedMaxLevelException() {
		super("Tried to upgrade something, like a perk, which already is at its maximum level.");
	}
}
