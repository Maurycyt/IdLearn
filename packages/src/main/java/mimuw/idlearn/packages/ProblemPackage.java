package mimuw.idlearn.packages;

import mimuw.idlearn.utils.ShellExecutor;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a single algorithmic problem package.
 */
public class ProblemPackage {
	/**
	 * Represents an example file pair config.
 	 */
	public static class Example {
		public String input;
		public String output;
	}

	/**
	 * Represents a configuration of a package given in the config file.
	 */
	public static class Config {
		public String title;
		public String statement;
		public ArrayList<Example> examples;
		public String testInput;
		public String userOutput;
		public ArrayList<Integer> testData;
	}

	/**
	 * The directory containing the package (one package).
	 */
	private final File packageDirectory;

	/**
	 * The parsed config.yml file.
	 */
	private final Config config;

	/**
	 * Scanner of the input file.
	 */
	private Scanner inputScanner;

	/**
	 * Writer to the output file.
	 */
	private FileWriter outputWriter;

	/**
	 * Interprets the given File as a directory containing one problem package.
	 * The directory must follow the template as given <a href="https://gitlab.com/Maurycyt/idlearn/-/wikis/Problem-Packages">here</a>
	 * @param packageDirectory The File object representing the directory with the package.
	 */
	public ProblemPackage(File packageDirectory) {
		this.packageDirectory = packageDirectory;
		Config tmpConfig;
		try {
			tmpConfig = (new Yaml(new Constructor(Config.class))).load(Files.readString(Path.of(packageDirectory.toString(), "config.yml")));
		} catch (IOException e) {
			tmpConfig = new Config();
			e.printStackTrace();
		}
		config = tmpConfig;
		if (!checkValidity()) {
			throw new RuntimeException("Package directory invalid.");
		}
	}

	public File getPackageDirectory() {
		return packageDirectory;
	}

	/**
	 * Returns the title of the problem, which is the name of the directory with the package.
	 * @return The problem title.
	 */
	public String getTitle() {
		return config.title;
	}

	/**
	 * Returns the statement of the problem, complete with an example input and output.
	 * @return The statement, with example input and output.
	 */
	public String getStatement() {
		StringBuilder sb = new StringBuilder();
		try {
			String contents = Files.readString(Path.of(packageDirectory.toString(), config.statement));
			sb.append(contents);
			if (config.examples.size() > 0) {
				if (config.examples.size() > 1) {
					sb.append("\n\nExamples:");
				} else {
					sb.append("\n\nExample:");
				}
				for (Example example : config.examples) {
					sb.append("\n\nInput:\n");
					contents = Files.readString(Path.of(packageDirectory.toString(), example.input));
					sb.append(contents);
					sb.append("\nOutput:\n");
					contents = Files.readString(Path.of(packageDirectory.toString(), example.output));
					sb.append(contents);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * Checks if the package contains all the necessary files.
	 * @return True if and only if the package contains all the necessary files.
	 */
	public boolean checkValidity() {
		boolean result;

		result = new File(packageDirectory, "makefile").exists();
		result &= new File(packageDirectory, "config.yml").exists();
		result &= new File(packageDirectory, config.statement).exists();
		for (Example example : config.examples) {
			result &= new File(packageDirectory, example.input).exists();
			result &= new File(packageDirectory, example.output).exists();
		}

		return result;
	}

	/**
	 * Builds the required executables.
	 */
	public void build() {
		ShellExecutor se = new ShellExecutor();
		se.addLineToScript("cd " + packageDirectory);
		se.addLineToScript("make all -s");
		se.execute();
	}

	/**
	 * Generates a test input file.
	 * @param id The number of the test to be generated.
	 */
	public void generateTestInput(int id) {
		if (inputScanner != null) {
			inputScanner.close();
			inputScanner = null;
		}
		if (outputWriter != null) {
			try {
				outputWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ShellExecutor se = new ShellExecutor();
		se.addLineToScript("cd " + packageDirectory);
		se.addLineToScript("make input -s TestID=" + id);
		se.execute();
		try {
			resetIO();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets a scanner to the most recently generated test input file.
	 * @return The scanner to the input file.
	 */
	public Scanner getTestInputScanner() {
		return inputScanner;
	}

	/**
	 * Generates a test model output file.
	 */
	public void generateTestModelOutput() {
		ShellExecutor se = new ShellExecutor();
		se.addLineToScript("cd " + packageDirectory);
		se.addLineToScript("make model.out -s");
		se.execute();
	}

	/**
	 * Prepares all there is to prepare in the package.
	 * Builds all executables, generates input and model output.
	 * @param id The ID of the test.
	 */
	public void prepareTest(int id) {
		build();
		generateTestInput(id);
		generateTestModelOutput();
	}

	/**
	 * Gets a writer to the user output file.
	 * @return The writer to the user output file.
	 */
	public FileWriter getTestOutputWriter() {
		return outputWriter;
	}

	/**
	 * Checks whether the user output is correct.
	 * Runs the checker on the input file, user output file and model output file.
	 * @return True if and only if the user output is correct.
	 */
	public boolean checkTest() {
		String checkerOutput;
		ShellExecutor se = new ShellExecutor();
		se.addLineToScript("cd " + packageDirectory);
		se.addLineToScript("make check -s");
		checkerOutput = se.execute();
		return checkerOutput.equals("OK\n");
	}

	/**
	 * Cleans the contents of the package.
	 */
	public void clean() {
		ShellExecutor se = new ShellExecutor();
		se.addLineToScript("cd " + packageDirectory);
		se.addLineToScript("make clean");
		se.execute();
	}

	/**
	 * Resets the file scanner by creating a new one.
	 */
	public void resetScanner() throws FileNotFoundException {
		inputScanner = new Scanner(new File(packageDirectory, config.testInput));
	}

	/**
	 * Resets the file writer by creating a new one.
	 */
	public void resetWriter() throws IOException {
		outputWriter = new FileWriter(new File(packageDirectory, config.userOutput));
	}

	/**
	 * Resets both the file scanner and writer.
	 */
	public void resetIO() throws IOException {
		resetScanner();
		resetWriter();
	}

	/**
	 * Returns the config.
	 */
	public Config getConfig() {
		return config;
	}

	/**
	 * Returns the testData member of the config.
	 */
	public ArrayList<Integer> getTestData() {
		return config.testData;
	}
}
