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
	public void packageManagerTest() {
		ShellExecutor.execute("rm -rf " + expectedPackageDirectoryPath);

		assertEquals(PackageManager.getInitialProblemPackageDirectory(), expectedPackageDirectory);

		String[] contents = expectedPackageDirectory.list();

		assertNotEquals(null, contents);
		assert contents != null;

		boolean copiedUtils = false;
		boolean didNotCopyTest = true;
		for (String filename : contents) {
			copiedUtils |= filename.equals("packageUtils.h");
			didNotCopyTest &= !filename.equals("test");
		}
		assertTrue(copiedUtils && didNotCopyTest);

		ProblemPackage[] problemPackages = PackageManager.getInitialProblemPackages();
		assertEquals(problemPackages, PackageManager.getProblemPackages());
		assertEquals(expectedPackageDirectory, PackageManager.getProblemPackageDirectory());
	}

	@Test
	public void problemPackageTest() throws IOException {
		ProblemPackage pack = PackageManager.getProblemPackages()[0];
		assertEquals(pack.getTitle(), "Addition");

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
						contents.length == 3 &&
						contents[0].equals("doc") &&
						contents[1].equals("makefile") &&
						contents[2].equals("prog")
		);
	}
}
