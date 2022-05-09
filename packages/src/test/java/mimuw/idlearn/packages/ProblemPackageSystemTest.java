package mimuw.idlearn.packages;

import mimuw.idlearn.utils.ShellExecutor;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ProblemPackageSystemTest {
	static final Path expectedPackageDirectoryPath = Path.of(System.getProperty("user.home"), ".idlearn", "problems");
	static final File expectedPackageDirectory = expectedPackageDirectoryPath.toFile();

	@Test
	public void testPackageManager() {
		ShellExecutor.execute("rm -rf " + expectedPackageDirectoryPath);

		assertEquals(PackageManager.getProblemPackageDirectory(), expectedPackageDirectory);

		String[] contents = expectedPackageDirectory.list();

		assertNotEquals(null, contents);
		assert contents != null;

		boolean copiedUtils = false;
		for (String filename : contents) {
			copiedUtils |= filename.equals("packageUtils.h");
		}
		assertTrue(copiedUtils);

		Map<String, ProblemPackage> problemPackages = PackageManager.getProblemPackages();
		assertEquals(problemPackages, PackageManager.getProblemPackages());
		assertEquals(expectedPackageDirectory, PackageManager.getProblemPackageDirectory());
	}

	@Test
	public void testProblemPackage() throws IOException {
		PackageManager.reloadProblemPackages();
		ProblemPackage pack = PackageManager.getProblemPackage("Addition");
		assertEquals(pack.getTitle(), "Addition");
		assertEquals(pack.getDifficulty(), ProblemPackage.Config.Difficulty.Easy);
		assertNotEquals(pack.getDifficulty(), ProblemPackage.Config.Difficulty.Medium);
		assertEquals(pack.getStatement(), """
						Johnny needs help with his maths homework. Write a program, which will read two numbers and output their sum.

						Input:
						The first and only line of input contains two numbers, a and b, in the range [-10^9, 10^9].

						Output:
						The only number outputted should be the value of a + b.

						Examples:
						
						Input:
						3 -5
						Output:
						-2
						
						Input:
						69 -27
						Output:
						42""");

		pack.prepareTest(123);
		Scanner scanner = pack.getTestInputScanner();
		int result = scanner.nextInt() + scanner.nextInt();
		assertFalse(scanner.hasNext());

		FileWriter writer = pack.getTestOutputWriter();
		writer.write(String.valueOf(result));
		writer.flush();
		assertTrue(pack.checkTest());

		// repeat to test creation of new scanners and writers.
		pack.prepareTest(123);
		scanner = pack.getTestInputScanner();
		result = scanner.nextInt() + scanner.nextInt();
		assertFalse(scanner.hasNext());

		writer = pack.getTestOutputWriter();
		writer.write(String.valueOf(result));
		writer.flush();
		assertTrue(pack.checkTest());

		// clean the package
		pack.clean();
		File packageDirectory = pack.getPackageDirectory();
		String [] contents = packageDirectory.list();
		assert contents != null;
		Arrays.sort(contents);
		assertTrue(
						contents.length == 5 &&
						contents[0].equals("config.yml") &&
						contents[1].equals("doc") &&
						contents[2].equals("makefile") &&
						contents[3].equals("makefile.in") &&
						contents[4].equals("prog")
		);
	}
}
