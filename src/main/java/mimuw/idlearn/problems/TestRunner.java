package mimuw.idlearn.problems;

import mimuw.idlearn.language.base.*;
import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.language.exceptions.SimulationException;

import java.util.ArrayList;

/**
 * Runs multiple tests of a package on a single solution.
 * Aggregates time statistics.
 */
public class TestRunner {
	private ProblemPackage pack;
	private Expression<Void> solution;
	private ArrayList<Integer> testData;

	public TestRunner(ProblemPackage pack, Expression<Void> solution) {
		this.pack = pack;
		this.solution = solution;
		this.testData = pack.getTestData();
	}

	public double aggregateTestTimes() throws SimulationException {
		double result = 1.0;

		TimeCounter timeCounter = new TimeCounter();
		for (Integer TestID : testData) {
			pack.prepareTest(TestID);
			timeCounter.clear();
			solution.evaluate(new Scope(), timeCounter);
			result *= timeCounter.getTime();
		}

		return Math.pow(result, 1.0 / testData.size());
	}
}