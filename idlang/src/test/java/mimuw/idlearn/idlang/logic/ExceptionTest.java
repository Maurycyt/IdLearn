package mimuw.idlearn.idlang.logic;

import mimuw.idlearn.idlang.logic.base.*;
import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.*;
import mimuw.idlearn.idlang.logic.keywords.Assignment;
import mimuw.idlearn.idlang.logic.keywords.Block;
import mimuw.idlearn.idlang.logic.keywords.While;
import mimuw.idlearn.idlang.logic.operators.TwoArgOperator;
import mimuw.idlearn.packages.PackageManager;
import mimuw.idlearn.packages.ProblemPackage;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionTest {
	@Test
	public void testTimeLimit() {
		final int N = (int)(TimeCounter.MAX_TIME / 3);
		Scope scope = new Scope();

		Variable i = new Variable("i", scope, new Value(Type.Integer, N));
		Constant zero = new Constant(new Value(Type.Integer, 0));

		var whileCond = TwoArgOperator.newGreaterEqual(i, zero);
		var whileBlock = new Block(
						new Assignment("i",
										TwoArgOperator.newSubtract(i, new Constant(new Value(Type.Integer, 1))),
										false
						)
		);
		var outerBlock = new Block(
						new While(whileCond, whileBlock)
		);

		assertThrows(TimeoutException.class, () -> outerBlock.evaluate(scope, new TimeCounter(), null, null));
	}

	@Test
	public void testUndefinedVariable() {
		Scope scope = new Scope();

		Assignment assignment = new Assignment("i",
						new Variable(Type.Integer, "i"),
						false
		);

		assertThrows(UndefinedVariableException.class, () -> assignment.evaluate(scope, new TimeCounter(), null, null));
	}
	@Test
	public void testBadType() throws SimulationException {
		Scope scope = new Scope();
		Expression var1 = new Assignment("a", new Constant(0), false);
		Expression var2 = new Variable(Type.Null, "a");
		var1.evaluate(scope, null, null, null);

		assertThrows(BadTypeException.class, () -> var2.evaluate(scope, null, null, null));

	}

	@Test
	public void testEndOfInput() {
		try {
			try {
				PackageManager.reloadProblemPackages();
			} catch (RuntimeException e) {
				System.out.println("Package directory altered. Reloading packages...");
				PackageManager.reloadProblemPackageDirectory(true);
				PackageManager.reloadProblemPackages();
			}
			ProblemPackage pkg = PackageManager.getProblemPackage("Addition");
			pkg.prepareTest(123);

			Scope scope = new Scope();
			Variable x = new Variable(Type.Integer, "x");
			Variable y = new Variable(Type.Integer, "y");
			Variable z = new Variable(Type.Integer, "z");
			InputHandler inputHandler = new InputHandler(x, y, z);

			assertThrows(EndOfInputException.class, () -> inputHandler.evaluate(
					scope,
					new TimeCounter(),
					pkg.getTestInputScanner(123),
					pkg.getTestOutputWriter(123)
			));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
