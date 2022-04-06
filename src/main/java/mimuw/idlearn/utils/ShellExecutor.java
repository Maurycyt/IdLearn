package mimuw.idlearn.utils;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

/**
 * Executes shell commands and creates executable shell scripts.
 */
public class ShellExecutor {
	/**
	 * Path to the system temporary file directory.
	 */
	private final static Path tmpDirPath = Path.of(System.getProperty("java.io.tmpdir"), "Idlearn");

	/**
	 * The default contents of a new temporary script file.
	 */
	private final static String defaultScriptContents = "#!/bin/bash\n\n";

	/**
	 * The temporary shell script file path.
	 */
	private final Path scriptFilePath;

	/**
	 * The temporary shell script file.
	 */
	private final File scriptFile;

	/**
	 * The object which will write to the script file.
	 */
	private FileWriter writer = null;

	private void prepareWriter(boolean closePrevious, boolean append) {
		try {
			if (closePrevious) {
				writer.close();
			}
			writer = new FileWriter(scriptFile, append);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds raw content to the end of the script file.
	 * @param content The new content to be appended.
	 */
	private void addContent(String content) {
		assert writer != null;
		try {
			writer.write(content);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new ShellExecutor object with a temporary shell script file.
	 */
	public ShellExecutor() {
		// the result file path, the value of which will later be assigned to scriptFilePath
		Path tempPath = null;
		// the temporary file directory
		File tmpDirFile = tmpDirPath.toFile();
		// creates the temporary file directory if necessary
		tmpDirFile.mkdirs();
		// prepares file permissions (700, hopefully)
		Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwx------");
		try {
			// creates the temporary file in the directory (/tmp/Idlearn/ on ubuntu linux, for example).
			tempPath = Files.createTempFile(tmpDirPath,"IdlearnSEScript", ".sh", PosixFilePermissions.asFileAttribute(perms));
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert tempPath != null;
		scriptFilePath = tempPath;
		scriptFile = scriptFilePath.toFile();
		scriptFile.deleteOnExit();

		// Write to the file, adding "#!/bin/bash"
		prepareWriter(false, false);
		addContent(defaultScriptContents);
	}

	/**
	 * Returns the default contents of a temporary script file.
	 * @return The contents of the script file.
	 */
	public static String getDefaultScriptContents() {
		return defaultScriptContents;
	}

	/**
	 * Returns the temporary script file.
	 * @return The script file.
	 */
	public File getScriptFile() {
		return scriptFile;
	}

	/**
	 * Returns the path to the temporary script file.
	 * @return The path to the script file.
	 */
	public Path getScriptFilePath() {
		return scriptFilePath;
	}

	/**
	 * Adds a given command to the end of the script file.
	 * @param command The command to be added.
	 */
	public void addLineToScript(String command) {
		addContent(command + "\n");
	}

	public void clearScript() {
		prepareWriter(true, false);
		addContent(defaultScriptContents);
	}

	/**
	 * Executes the given command and returns its output.
	 * @param command The command to execute.
	 * @return The output of the executed command. Often empty.
	 */
	public static String execute(String command) {
		// StringBuilder for the output of the command.
		StringBuilder output = new StringBuilder();

		try {
			Process p = Runtime.getRuntime().exec(command);
			int exitCode = p.waitFor();
			String line;

			// If command execution failed, throw unchecked CommandException
			if (exitCode != 0) {
				StringBuilder errors = new StringBuilder();
				BufferedReader stderr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				while ((line = stderr.readLine()) != null) {
					errors.append(line);
					errors.append("\n");
				}
				throw new CommandException(command, errors.toString());
			}

			// If all is well, capture the output.
			BufferedReader stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));

			while ((line = stdout.readLine())!= null) {
				output.append(line);
				output.append("\n");
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return output.toString();
	}

	/**
	 * Executes the created script.
	 */
	public String execute() {
		String output;
		String command = scriptFilePath.toString();

		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		output = execute(command);
		prepareWriter(false, true);
		return output;
	}
}
