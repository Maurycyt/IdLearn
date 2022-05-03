package mimuw.idlearn.testRunner;

import mimuw.idlearn.idlang.base.Expression;
import mimuw.idlearn.idlang.base.TimeCounter;
import mimuw.idlearn.idlang.environment.Scope;
import mimuw.idlearn.idlang.exceptions.SimulationException;
import mimuw.idlearn.packages.ProblemPackage;

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

	public double aggregateTestTimes() throws SimulationException, WrongAnswerException {
		double result = 1.0;

		TimeCounter timeCounter = new TimeCounter();
		for (Integer TestID : testData) {
			pack.prepareTest(TestID);
			timeCounter.clear();
			solution.evaluate(new Scope(), timeCounter);
			if (!pack.checkTest()) {
				throw new WrongAnswerException();
			}
			result *= timeCounter.getTime();
		}

		return Math.pow(result, 1.0 / testData.size());
	}
}