package mimuw.idlearn.packages;

import mimuw.idlearn.core.Emitter;
import mimuw.idlearn.utils.ShellExecutor;
import mimuw.idlearn.properties.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Static class, which manages problem packages.
 */
public class PackageManager {
	public static URL ProblemPackageDirectory = PackageManager.class.getResource("problems/");

	/**
	 * Reloads the problem package directory.
	 * Avoid using if the problem package directory does not need to be reloaded.
	 * Checks for the existence of problem "program files". If the directory
	 * doesn't exist or `forceCopy` is true, it creates it and copies the default contents into it.
	 */
	public static void reloadProblemPackageDirectory(boolean forceCopy) {
		Path problemPackageDirectoryPath = Path.of(Config.getDataPath().toString(), "problems");
		File result = problemPackageDirectoryPath.toFile();

		if (result.mkdirs() || forceCopy) {
			// If the problem package directory didn't exist, we need to initialize it.
			// Copy the contents of defaultProblemPackages into the directory.
			ShellExecutor.execute("cp -r " + ProblemPackageDirectory.getFile() + ". " + problemPackageDirectoryPath);
		}

		problemPackageDirectory = result;
	}

	public static void reloadProblemPackageDirectory() {
		reloadProblemPackageDirectory(false);
	}


	/**
	 * The problem package directory.
	 */
	private static File problemPackageDirectory = null;
	public static Emitter customTaskEmitter = new Emitter();

	/**
	 * Reloads the array of problem packages in the program files.
	 * Avoid using if problem packages do not need to be reloaded.
	 */
	public static void reloadProblemPackages() {
		if (problemPackageDirectory == null || problemPackageDirectory.list() == null) {
			reloadProblemPackageDirectory();
		}

		File [] packageDirectories = problemPackageDirectory.listFiles(File::isDirectory);
		if (packageDirectories == null) {
			throw new RuntimeException("Problem package directory empty");
		}

		HashMap<String, ProblemPackage> result = new HashMap<>();
		Set<String> titles = new HashSet<>();
		for (File packageDirectory : packageDirectories) {
			ProblemPackage pack = new ProblemPackage(packageDirectory);
			result.put(pack.getTitle(), pack);
			titles.add(pack.getTitle());
		}

		// Award achievement for custom problem package.
		if (!titles.equals(Config.getDefaultTaskTitles())) {
			customTaskEmitter.fire(0);
		}

		problemPackages = result;
	}

	/**
	 * The problem package directory.
	 */
	private static HashMap<String, ProblemPackage> problemPackages = null;

	/**
	 * Gets the problem package directory.
	 * @return The problem package directory.
	 */
	public static File getProblemPackageDirectory() {
		if (problemPackageDirectory == null || problemPackageDirectory.list() == null) {
			reloadProblemPackageDirectory();
		}
		return problemPackageDirectory;
	}

	/**
	 * Gets the array of problem packages.
	 * @return The array of problem packages.
	 */
	public static Map<String, ProblemPackage> getProblemPackages() {
		if(problemPackages == null) {
			reloadProblemPackages();
		}
		return problemPackages;
	}

	/**
	 * Gets a specific problem package by title.
	 * @param title The title of the package.
	 * @return The requested package.
	 */
	public static ProblemPackage getProblemPackage(String title) throws FileNotFoundException {
		if (problemPackages == null) {
			reloadProblemPackages();
		}
		if (problemPackages.containsKey(title)) {
			return problemPackages.get(title);
		}
		throw new FileNotFoundException("Package not found.");
	}
}
