package mimuw.idlearn.problems;

import java.io.File;

public class ProblemPackage {
	private final String title;

	public ProblemPackage(File packageDirectory) {
		title = packageDirectory.getName();
	}

	public String getTitle() {
		return title;
	}
}
