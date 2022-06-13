package mimuw.idlearn.idlang.logic;

import mimuw.idlearn.idlang.logic.base.*;
import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;
import mimuw.idlearn.idlang.logic.keywords.Assignment;
import mimuw.idlearn.idlang.logic.keywords.Block;
import mimuw.idlearn.idlang.logic.keywords.If;
import mimuw.idlearn.idlang.logic.keywords.While;
import mimuw.idlearn.idlang.logic.operators.TwoArgOperator;
import mimuw.idlearn.packages.PackageManager;
import mimuw.idlearn.packages.ProblemPackage;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class FlowTest {
	@Test
	public void testSimpleIfElse() throws SimulationException {
		Scope scope = new Scope();

		Variable x = new Variable(Type.Long, "x");
		new Assignment("x", new Constant(5), false).evaluate(scope, new ResourceCounter(), null, null);

		Block onTrue = new Block(new ArrayList<>(List.of(
						new Assignment("x",
										TwoArgOperator.newAdd(x, new Constant(1)),
										false)
		)));
		Block onFalse = new Block(new ArrayList<>(List.of(
						new Assignment("x",
										TwoArgOperator.newAdd(x, new Constant(-1)),
										false)
		)));

		Value cond = TwoArgOperator.newEqual(
						TwoArgOperator.newModulo(
										new Constant(scope.getVariable("x")),
										new Constant(2)),
						new Constant(0)
		).evaluate(scope, new ResourceCounter(), null, null);

		// `if (x % 2 == 0) {x++;} else {x--;}`
		new If(new Constant(cond), onTrue, onFalse).evaluate(scope, new ResourceCounter(), null, null);

		assertEquals(4L, scope.getVariable("x").value);
	}

	@Test
	public void testSimpleWhile() throws SimulationException {
		Scope scope = new Scope();

		Variable x = new Variable(Type.Long, "x");
		Variable count = new Variable(Type.Long, "count");
		new Assignment("x", new Constant(0), false).evaluate(scope, new ResourceCounter(), null, null);
		new Assignment("count", new Constant(420), false).evaluate(scope, new ResourceCounter(), null, null);

		// Note: don't hard-code evaluations in operators unless you want endless loops!
		// - write `x` instead of `x.evaluate(scope)`
		// - write `scope.getVariable("x")` instead of `scope.getVariable("x").value`

		var cond = TwoArgOperator.newGreater(
						count,
						new Constant(0)
		);

		Block block = new Block(new ArrayList<>(List.of(
						new Assignment("x",
										TwoArgOperator.newAdd(x, new Constant(1)),
										false),
						new Assignment("count",
										TwoArgOperator.newSubtract(count, new Constant(1)),
										false)
		)));

		// `while (count > 0) {x++; count--}`
		new While(cond, block).evaluate(scope, new ResourceCounter(), null, null);

		assertEquals(420L, x.evaluate(scope, new ResourceCounter(), null, null).value);
	}

	@Test
	public void testInnerScopeAssignment() throws SimulationException {
		Scope outerScope = new Scope();
		Scope innerScope = new Scope(outerScope);

		Variable x = new Variable(Type.Long, "x");
		new Assignment("x", new Constant(1), false).evaluate(outerScope, new ResourceCounter(), null, null);
		new Assignment("y", x, false).evaluate(innerScope, new ResourceCounter(), null, null);
		new Assignment("x",
						TwoArgOperator.newAdd(x, new Constant(1)),
						false
		).evaluate(innerScope, new ResourceCounter(), null, null);

		assertEquals(2L, innerScope.getVariable("x").value);
		assertEquals(2L, outerScope.getVariable("x").value);
		assertEquals(1L, innerScope.getVariable("y").value);
	}

	@Test
	public void testProgramFib() throws SimulationException {
		final int N = 20;
		Scope scope = new Scope();

		Variable f1 = new Variable("f1", scope, 0);
		Variable f2 = new Variable("f2", scope, 1);
		Variable f3 = new Variable(Type.Long, "f3", scope);
		Variable n = new Variable("n", scope, N);
		Variable i = new Variable("i", scope, 0);

		var whileCond = TwoArgOperator.newLessEqual(i, n);
		var whileBlock = new Block(
						new Assignment("f3", TwoArgOperator.newAdd(f1, f2), false),
						new Assignment("f1", f2, false),
						new Assignment("f2", f3, false),
						new Assignment("i", TwoArgOperator.newAdd(i, new Constant(1)), false)
		);

		new Block(
						new Assignment("n", TwoArgOperator.newSubtract(n, new Constant(3)), false),
						new While(whileCond, whileBlock)
		).evaluate(scope, new ResourceCounter(), null, null);

		assertEquals(4181L, f3.evaluate(scope, new ResourceCounter(), null, null).value);
	}

	@Test
	public void testInputAndOutput() throws SimulationException {
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
			Variable x = new Variable(Type.Long, "x");
			Variable y = new Variable(Type.Long, "y");
			InputHandler inputHandler = new InputHandler(x, y);

			Scanner inputScanner = pkg.getTestInputScanner(123);
			Writer outputWriter = pkg.getTestOutputWriter(123);

			inputHandler.evaluate(scope, new ResourceCounter(), inputScanner, outputWriter);

			Expression ret = TwoArgOperator.newAdd(x, y);
			OutputHandler outputHandler = new OutputHandler(ret);
			outputHandler.evaluate(scope, new ResourceCounter(), inputScanner, outputWriter);

			assertTrue(pkg.checkTest(123));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
