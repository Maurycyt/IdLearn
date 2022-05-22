package mimuw.idlearn.scoring;

import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.ResourceCounter;
import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;
import mimuw.idlearn.idlang.logic.exceptions.TimeoutException;
import mimuw.idlearn.idlang.logic.exceptions.WrongAnswerException;
import mimuw.idlearn.packages.ProblemPackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Semaphore;

/**
 * Runs multiple tests of a package on a single solution.
 * Aggregates time statistics.
 */
public class TestRunner {
	private final ProblemPackage pack;
	private final Expression solution;
	private final ArrayList<Integer> testData;
	private static final Semaphore mutex = new Semaphore(1);

	public TestRunner(ProblemPackage pack, Expression solution) {
		this.pack = pack;
		this.solution = solution;
		this.testData = pack.getTestData();
	}

	public double aggregateTestTimes() throws SimulationException, IOException {
		double result = 1.0;

		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
			throw new TimeoutException(ResourceCounter.MAX_TIME);
		}

		pack.build();
		// Prepare futures which run tests and propagate exceptions.
		ArrayList<CompletableFuture<Double>> futures = new ArrayList<>();
		for (Integer TestID : testData) {
			futures.add(CompletableFuture.supplyAsync(() -> {
				pack.prepareTest(TestID);
				ResourceCounter resourceCounter = new ResourceCounter();
				try {
					solution.evaluate(new Scope(), resourceCounter, pack.getTestInputScanner(TestID), pack.getTestOutputWriter(TestID));
					if (!pack.checkTest(TestID)) {
						throw new CompletionException(new WrongAnswerException());
					}
				} catch (SimulationException | IOException e) {
					throw new CompletionException(e);
				}
				return resourceCounter.getTime();
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
				} catch (SimulationException | IOException possible) {
					mutex.release();
					throw possible;
				} catch (Throwable impossible) {
					throw new AssertionError(impossible);
				}
			}
		}

		mutex.release();

		// Return the geometric mean of the collected times.
		return Math.pow(result, 1.0 / testData.size());
	}
}