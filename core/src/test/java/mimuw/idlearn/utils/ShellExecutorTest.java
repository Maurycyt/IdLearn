package mimuw.idlearn.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ShellExecutorTest {
	private ShellExecutor se;
	private File scriptFile;
	private Path scriptFilePath;

	private void init() {
		se = new ShellExecutor();
		scriptFile = se.getScriptFile();
		scriptFilePath = se.getScriptFilePath();
	}

	@Test
	public void testFileCreation() throws IOException {
		init();
		Assertions.assertTrue(scriptFile.canRead() && scriptFile.canWrite() && scriptFile.canExecute());
		String expectedContents = ShellExecutor.getDefaultScriptContents();
		String actualContents = Files.readString(scriptFilePath);
		Assertions.assertEquals(expectedContents, actualContents);
	}

	@Test
	public void testCommandExecution() {
		init();
		String someText = "some text";
		String expectedOutput = someText + "\n";
		String output;

		// Check execution of single command.
		output = ShellExecutor.execute("echo " + someText);
		Assertions.assertEquals(expectedOutput, output);

		// Check execution of wrong command.
		Assertions.assertThrows(CommandException.class, () -> ShellExecutor.execute("rm /"));

		// Check script appending and script execution.
		se.addLineToScript("echo " + someText);
		output = se.execute();
		Assertions.assertEquals(expectedOutput, output);

		// Now check if appending to a script which was executed works properly.
		se.addLineToScript("echo " + someText);
		expectedOutput += someText + "\n";
		output = se.execute();
		Assertions.assertEquals(expectedOutput, output);

		// Now check if cleaning a script works as intended.
		se.clearScript();
		expectedOutput = "";
		output = se.execute();
		Assertions.assertEquals(expectedOutput, output);
	}
}
