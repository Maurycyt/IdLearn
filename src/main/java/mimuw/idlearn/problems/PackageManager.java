package mimuw.idlearn.problems;

import mimuw.idlearn.utils.ShellExecutor;

import java.io.File;
import java.nio.file.Path;

/**
 * Static class, which manages problem packages.
 */
public class PackageManager {
	/**
	 * Gets the initial value of the problem package directory.
	 * Will be invoked before the first use of the PackageManager class.
	 * Shouldn't be used to retrieve the value of problemPackageDirectory.
	 * Checks for the existence of problem "program files". If the directory
	 * doesn't exist, it creates it and copies the default contents into it.
	 * @return The File object representing the problem package directory.
	 */
	private static File getInitialProblemPackageDirectory() {
		Path problemPackageDirectoryPath = Path.of(System.getProperty("user.home"), ".idlearn", "problems");
		File result = problemPackageDirectoryPath.toFile();

		if (result.mkdirs()) {
			String copyCommand = "cp -r defaultProblemPackages/. " + problemPackageDirectoryPath;
			ShellExecutor.execute(copyCommand);
		}

		return result;
	}

	/**
	 * The problem package directory.
	 */
	private static final File problemPackageDirectory = getInitialProblemPackageDirectory();

	/**
	 * Gets the initial array of problem packages in the program files.
	 * @return The initial array of problem packages.
	 */
	private static ProblemPackage [] getInitialProblemPackages() {
		File [] packageDirectories = problemPackageDirectory.listFiles(File::isDirectory);
		assert packageDirectories != null;
		ProblemPackage [] result = new ProblemPackage [packageDirectories.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = new ProblemPackage(packageDirectories[i]);
		}
		return result;
	}

	/**
	 * The problem package directory.
	 */
	private static final ProblemPackage [] problemPackages = getInitialProblemPackages();

	/**
	 * Gets the problem package directory.
	 * @return The problem package directory.
	 */
	public static File getProblemPackageDirectory() {
		return problemPackageDirectory;
	}

	/**
	 * Gets the array of problem packages.
	 * @return The array of problem packages.
	 */
	public static ProblemPackage [] getProblemPackages() {
		return problemPackages;
	}
}
