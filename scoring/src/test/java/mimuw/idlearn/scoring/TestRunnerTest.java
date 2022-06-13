package mimuw.idlearn.scoring;

import mimuw.idlearn.idlang.logic.base.*;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;
import mimuw.idlearn.idlang.logic.exceptions.WrongAnswerException;
import mimuw.idlearn.idlang.logic.keywords.Assignment;
import mimuw.idlearn.idlang.logic.keywords.Block;
import mimuw.idlearn.idlang.logic.operators.TwoArgOperator;
import mimuw.idlearn.packages.PackageManager;
import mimuw.idlearn.packages.ProblemPackage;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestRunnerTest {
	@Test
	public void testTestRunner() throws SimulationException, IOException {
		PackageManager.reloadProblemPackages();
		ProblemPackage pack = PackageManager.getProblemPackage("Addition");
		assertEquals(pack.getTitle(), "Addition");

		Expression solution = new Block(
				new InputHandler(new Variable(Type.Long, "x"), new Variable(Type.Long, "y")),
				new Assignment("x", TwoArgOperator.newAdd(new Variable(Type.Long, "x"), new Variable(Type.Long, "y")), false),
				new OutputHandler(new Variable(Type.Long, "x"))
		);

		TestRunner testRunner = new TestRunner(pack, solution);

		double result = 0;
		try {
			result = testRunner.aggregateTestTimes();
		} catch (WrongAnswerException e) {
			fail();
		}
		System.out.println(pack.getTitle() + " ran with score: " + result);

		assertEquals(3, result, 0.1);

		Expression badSolution = new Block(
				new OutputHandler(new Constant(0))
		);

		testRunner = new TestRunner(pack, badSolution);

		assertThrows(WrongAnswerException.class, testRunner::aggregateTestTimes);
	}
}
