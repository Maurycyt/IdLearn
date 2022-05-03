package mimuw.idlearn.packages;

import mimuw.idlearn.utils.ShellExecutor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Scanner;

public class ProblemPackageSystemTest {
	static final Path expectedPackageDirectoryPath = Path.of(System.getProperty("user.home"), ".idlearn", "problems");
	static final File expectedPackageDirectory = expectedPackageDirectoryPath.toFile();

	@Test
	public void testPackageManager() {
		ShellExecutor.execute("rm -rf " + expectedPackageDirectoryPath);

		Assertions.assertEquals(PackageManager.getProblemPackageDirectory(), expectedPackageDirectory);

		String[] contents = expectedPackageDirectory.list();

		Assertions.assertNotEquals(null, contents);
		assert contents != null;

		boolean copiedUtils = false;
		for (String filename : contents) {
			copiedUtils |= filename.equals("packageUtils.h");
		}
		Assertions.assertTrue(copiedUtils);

		ProblemPackage[] problemPackages = PackageManager.getProblemPackages();
		Assertions.assertEquals(problemPackages, PackageManager.getProblemPackages());
		Assertions.assertEquals(expectedPackageDirectory, PackageManager.getProblemPackageDirectory());
	}

	@Test
	public void testProblemPackage() throws IOException {
		PackageManager.reloadProblemPackages();
		ProblemPackage pack = PackageManager.getProblemPackages()[0];
		Assertions.assertEquals(pack.getTitle(), "Addition");
		Assertions.assertEquals(pack.getStatement(), """
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
		Assertions.assertFalse(scanner.hasNext());

		FileWriter writer = pack.getTestOutputWriter();
		writer.write(String.valueOf(result));
		writer.flush();
		Assertions.assertTrue(pack.checkTest());

		// repeat to test creation of new scanners and writers.
		pack.prepareTest(123);
		scanner = pack.getTestInputScanner();
		result = scanner.nextInt() + scanner.nextInt();
		Assertions.assertFalse(scanner.hasNext());

		writer = pack.getTestOutputWriter();
		writer.write(String.valueOf(result));
		writer.flush();
		Assertions.assertTrue(pack.checkTest());

		// clean the package
		pack.clean();
		File packageDirectory = pack.getPackageDirectory();
		String [] contents = packageDirectory.list();
		assert contents != null;
		Arrays.sort(contents);
		Assertions.assertTrue(
						contents.length == 5 &&
						contents[0].equals("config.yml") &&
						contents[1].equals("doc") &&
						contents[2].equals("makefile") &&
						contents[3].equals("makefile.in") &&
						contents[4].equals("prog")
		);
	}
}
