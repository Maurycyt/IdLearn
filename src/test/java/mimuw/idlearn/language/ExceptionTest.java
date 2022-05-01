package mimuw.idlearn.language;

import mimuw.idlearn.language.base.*;
import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.language.exceptions.EndOfInputException;
import mimuw.idlearn.language.exceptions.SimulationException;
import mimuw.idlearn.language.exceptions.TimeoutException;
import mimuw.idlearn.language.exceptions.UndefinedException;
import mimuw.idlearn.language.keywords.Assignment;
import mimuw.idlearn.language.keywords.Block;
import mimuw.idlearn.language.keywords.While;
import mimuw.idlearn.language.operators.TwoArgOperator;
import mimuw.idlearn.problems.PackageManager;
import mimuw.idlearn.problems.ProblemPackage;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionTest {
	@Test
	public void testTimeLimit() {
		final int N = (int)(TimeCounter.MAX_TIME / 3);
		Scope scope = new Scope();

		Variable<Integer> i = new Variable<>("i", scope, N);
		Value<Integer> zero = new Value<>(0);

		var whileCond = TwoArgOperator.newGreaterEqual(i, zero);
		var whileBlock = new Block(
						new Assignment<>("i",
										TwoArgOperator.newSubtract(i, new Value<>(1)),
										false
						)
		);
		var outerBlock = new Block(
						new While(whileCond, whileBlock)
		);

		assertThrows(TimeoutException.class, () -> outerBlock.evaluate(scope, new TimeCounter()));
	}

	@Test
	public void testUndefinedVariable() {
		Scope scope = new Scope();

		Assignment<Integer> assignment = new Assignment<>("i",
						new Variable<>("i"),
						false
		);

		assertThrows(UndefinedException.class, () -> assignment.evaluate(scope, new TimeCounter()));
	}

	@Test
	public void testEndOfInput() throws SimulationException {
		try {
			ProblemPackage pkg = PackageManager.getProblemPackage("Addition");
			pkg.prepareTest(123);

			Scope scope = new Scope();
			Variable<Integer> x = new Variable<>("x");
			Variable<Integer> y = new Variable<>("y");
			Variable<Integer> z = new Variable<>("z");
			InputHandler inputHandler = new InputHandler(pkg);

			inputHandler.takeVariables(x, y, z);
			assertThrows(EndOfInputException.class, () -> inputHandler.evaluate(scope, new TimeCounter()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
