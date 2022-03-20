package mimuw.idlearn.core;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.*;

public class ShellExecutorTest {
	private ShellExecutor se;
	private File scriptFile;
	private Path scriptFilePath;

	@SuppressWarnings("unused")
	@BeforeEach
	private void beforeEach() {
		se = new ShellExecutor();
		scriptFile = se.getScriptFile();
		scriptFilePath = se.getScriptFilePath();
	}

	@Test
	public void fileCreationTest() throws IOException {
		assertTrue(scriptFile.canRead() && scriptFile.canWrite() && scriptFile.canExecute());
		String expectedContents = ShellExecutor.getDefaultScriptContents();
		String actualContents = Files.readString(scriptFilePath);
		assertEquals(expectedContents, actualContents);
	}

	@Test
	public void commandExecutionTest() {
		String someText = "some text";
		String expectedOutput = someText + "\n";
		String output;

		// Check execution of single command.
		output = se.execute("echo " + someText);
		assertEquals(expectedOutput, output);

		// Check script appending and script execution.
		se.addLineToScript("echo " + someText);
		output = se.execute();
		assertEquals(expectedOutput, output);

		// Now check if appending to a script which was executed works properly.
		se.addLineToScript("echo " + someText);
		expectedOutput += someText + "\n";
		output = se.execute();
		assertEquals(expectedOutput, output);

		// Now check if cleaning a script works as intended.
		se.clearScript();
		expectedOutput = "";
		output = se.execute();
		assertEquals(expectedOutput, output);
	}
}
