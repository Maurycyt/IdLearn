package mimuw.idlearn.scoring;

import mimuw.idlearn.idlang.logic.base.*;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;
import mimuw.idlearn.idlang.logic.keywords.Assignment;
import mimuw.idlearn.idlang.logic.keywords.Block;
import mimuw.idlearn.idlang.logic.operators.TwoArgOperator;
import mimuw.idlearn.packages.PackageManager;
import mimuw.idlearn.packages.ProblemPackage;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class TestRunnerTest {
	@Test
	public void testTestRunner() throws SimulationException, FileNotFoundException {
		PackageManager.reloadProblemPackages();
		ProblemPackage pack = PackageManager.getProblemPackage("Addition");
		assertEquals(pack.getTitle(), "Addition");

		Expression<Void> solution = new Block(
				new InputHandler(pack, new Variable<>("x"), new Variable<>("y")),
				new Assignment<>("x", TwoArgOperator.newAdd(new Variable<>("x"), new Variable<>("y")), false),
				new OutputHandler(pack, new Variable<>("x"))
		);

		TestRunner testRunner = new TestRunner(pack, solution);

		double result = 0;
		try {
			result = testRunner.aggregateTestTimes();
		} catch (WrongAnswerException e) {
			fail();
		}
		System.out.println(pack.getTitle() + " ran with score: " + result);
		System.out.println();

		assertEquals(4, result, 0.1);

		Expression<Void> badSolution = new Block(
				new OutputHandler(pack, new Value<>(0))
		);

		testRunner = new TestRunner(pack, badSolution);

		assertThrows(WrongAnswerException.class, testRunner::aggregateTestTimes);
	}
}
