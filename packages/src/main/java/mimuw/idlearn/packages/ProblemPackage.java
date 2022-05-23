package mimuw.idlearn.packages;

import mimuw.idlearn.utils.ShellExecutor;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a single algorithmic problem package.
 */
public class ProblemPackage {

	/**
	 * Represents a configuration of a package given in the config file.
	 */
	public static class Config {
		/**
		 * Represents an example file pair config.
		 */
		public static class Example {
			public String input;
			public String output;
		}

		/**
		 * Represents the difficulty of a problem.
		 */
		public enum Difficulty {
			Easy,
			Medium,
			Hard
		}

		public String title;
		public Difficulty difficulty;
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
				for (Config.Example example : config.examples) {
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
		for (Config.Example example : config.examples) {
			result &= new File(packageDirectory, example.input).exists();
			result &= new File(packageDirectory, example.output).exists();
		}

		return result;
	}

	/**
	 * Builds the required executables for the package as a whole.
	 */
	public void build() {
		ShellExecutor.execute("make all -s", packageDirectory);
	}

	/**
	 * Generates a test input file.
	 * @param id The number of the test to be generated.
	 */
	public void generateTestInput(int id) {
		ShellExecutor.execute("make input -s TEST_ID=" + id, packageDirectory);
	}

	/**
	 * Generates a test model output file.
	 */
	public void generateTestModelOutput(int id) {
		ShellExecutor.execute("make modelOut -s TEST_ID=" + id, packageDirectory);
	}

	/**
	 * Prepares a single test in the package
	 * Generates input and model output.
	 * @param id The ID of the test.
	 */
	public void prepareTest(int id) {
		generateTestInput(id);
		generateTestModelOutput(id);
	}

	/**
	 * Checks whether the user output is correct.
	 * Runs the checker on the input file, user output file and model output file.
	 * @return True if and only if the user output is correct.
	 */
	public boolean checkTest(int id) {
		String checkerOutput = ShellExecutor.execute("make check -s TEST_ID=" + id, packageDirectory);
		return checkerOutput.equals("OK\n");
	}

	/**
	 * Gets a scanner to the most recently generated test input file.
	 * @param id The ID of the test.
	 * @return The scanner to the input file.
	 */
	public Scanner getTestInputScanner(int id) throws FileNotFoundException {
		return new Scanner(new File(packageDirectory, config.testInput + id));
	}

	/**
	 * Gets a writer to the user output file.
	 * @param id The ID of the test.
	 * @return The writer to the user output file.
	 */
	public FileWriter getTestOutputWriter(int id) throws IOException {
		return new FileWriter(new File(packageDirectory, config.userOutput + id));
	}

	/**
	 * Cleans the contents of the package.
	 */
	public void clean() {
		ShellExecutor.execute("make clean -s", packageDirectory);
	}

	/**
	 * Returns the config of the package.
	 * @return The config.
	 */
	public Config getConfig() {
		return config;
	}

	/**
	 * Returns the difficulty of the problem as a member of Config.Difficulty.
	 * @return The difficulty.
	 */
	public Config.Difficulty getDifficulty() {
		return config.difficulty;
	}

	/**
	 * Returns the testData member of the config.
	 * @return The test data.
	 */
	public ArrayList<Integer> getTestData() {
		return config.testData;
	}
}
