package mimuw.idlearn.problems;

import mimuw.idlearn.utils.ShellExecutor;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

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

		assertEquals(PackageManager.getInitialProblemPackageDirectory(), expectedPackageDirectory);

		String[] contents = expectedPackageDirectory.list();

		assertNotEquals(null, contents);
		assert contents != null;

		boolean copiedUtils = false;
		for (String filename : contents) {
			copiedUtils |= filename.equals("packageUtils.h");
		}
		assertTrue(copiedUtils);

		ProblemPackage[] problemPackages = PackageManager.getInitialProblemPackages();
		assertEquals(problemPackages, PackageManager.getProblemPackages());
		assertEquals(expectedPackageDirectory, PackageManager.getProblemPackageDirectory());
	}

	@Test
	public void testProblemPackage() throws IOException {
		ProblemPackage pack = PackageManager.getProblemPackages()[0];
		assertEquals(pack.getTitle(), "Addition");
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
