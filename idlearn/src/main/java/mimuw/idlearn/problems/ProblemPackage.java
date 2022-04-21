package mimuw.idlearn.problems;

import mimuw.idlearn.utils.ShellExecutor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Represents a single algorithmic problem package.
 */
public class ProblemPackage {
	/**
	 * The expected contents of a properly formatted package.
	 * Lists all files in the form of arrays of subdirectories relative
	 * to the package directory, for easy modification.
	 */
	private static final String [] [] expectedContents = {
					{"makefile"},
					{"doc", "statement.txt"},
					{"doc", "example.in"},
					{"doc", "example.out"},
					{"prog", "generator.cpp"},
					{"prog", "model.cpp"},
					{"prog", "checker.cpp"}
	};

	/**
	 * The directory containing the package (one package).
	 */
	private final File packageDirectory;

	/**
	 * The title of the package, which is the name of the directory.
	 */
	private final String title;

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
	 * The directory must follow the template as given here: https://gitlab.com/Maurycyt/idlearn/-/wikis/Problem-Packages
	 * @param packageDirectory The File object representing the directory with the package.
	 */
	public ProblemPackage(File packageDirectory) {
		this.packageDirectory = packageDirectory;
		title = packageDirectory.getName();
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
		return title;
	}

	/**
	 * Returns the statement of the problem, complete with an example input and output.
	 * @return The statement, with example input and output.
	 */
	public String getStatement() {
		StringBuilder sb = new StringBuilder();
		try {
			String contents = Files.readString(Path.of(packageDirectory.toString(), "doc", "statement.txt"));
			sb.append(contents);
			sb.append("\n\nExample input:\n");
			contents = Files.readString(Path.of(packageDirectory.toString(), "doc", "example.in"));
			sb.append(contents);
			sb.append("\nExample output:\n");
			contents = Files.readString(Path.of(packageDirectory.toString(), "doc", "example.out"));
			sb.append(contents);
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
		boolean result = true;

		for (String [] pathArray : expectedContents) {
			File expectedFile = packageDirectory;
			for (String subdirectory : pathArray) {
				expectedFile = new File(expectedFile, subdirectory);
			}
			result &= expectedFile.exists();
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
		inputScanner = new Scanner(new File(packageDirectory, "input.in"));
	}

	/**
	 * Resets the file writer by creating a new one.
	 */
	public void resetWriter() throws IOException {
		outputWriter = new FileWriter(new File(packageDirectory, "user.out"));
	}

	/**
	 * Resets both the file scanner and writer.
	 */
	public void resetIO() throws IOException {
		resetScanner();
		resetWriter();
	}
}
