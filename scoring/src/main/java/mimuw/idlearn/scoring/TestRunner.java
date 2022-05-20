package mimuw.idlearn.scoring;

import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.TimeCounter;
import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;
import mimuw.idlearn.idlang.logic.exceptions.WrongAnswerException;
import mimuw.idlearn.packages.ProblemPackage;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * Runs multiple tests of a package on a single solution.
 * Aggregates time statistics.
 */
public class TestRunner {
	private final ProblemPackage pack;
	private final Expression<Void> solution;
	private final ArrayList<Integer> testData;

	public TestRunner(ProblemPackage pack, Expression<Void> solution) {
		this.pack = pack;
		this.solution = solution;
		this.testData = pack.getTestData();
	}

	public double aggregateTestTimes() throws SimulationException {
		double result = 1.0;

		// Prepare futures which run tests and propagate exceptions.
		ArrayList<CompletableFuture<Double>> futures = new ArrayList<>();
		for (Integer TestID : testData) {
			futures.add(CompletableFuture.supplyAsync(() -> {
				pack.prepareTest(TestID);
				TimeCounter timeCounter = new TimeCounter();
				try {
					solution.evaluate(new Scope(), timeCounter);
					if (!pack.checkTest(TestID)) {
						throw new CompletionException(new WrongAnswerException());
					}
				} catch (SimulationException e) {
					throw new CompletionException(e);
				}
				return timeCounter.getTime();
			}));
		}

		// Join all the futures, catch exceptions and aggregate test times.
		for (CompletableFuture<Double> future : futures) {
			try {
				future.join();
				result *= future.getNow(0.0);
			} catch (CompletionException e) {
				try {
					throw e.getCause();
				} catch (SimulationException possible) {
					throw possible;
				} catch (Throwable impossible) {
					throw new AssertionError(impossible);
				}
			}
		}

		// Return the geometric mean of the collected times.
		return Math.pow(result, 1.0 / testData.size());
	}
}