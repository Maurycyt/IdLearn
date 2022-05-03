package mimuw.idlearn.utils;

/**
 * Unchecked exception, thrown when the execution of a command fails.
 */
public class CommandException extends RuntimeException {
	public CommandException(String command, String errors) {
		super(
			"Command\n>>>\n" +
			command +
			"\n<<< returned the following errors: >>>\n" +
			errors +
			"<<<"
		);
	}
}
