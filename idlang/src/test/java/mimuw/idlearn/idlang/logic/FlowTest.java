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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FlowTest {
	@Test
	public void testSimpleIfElse() throws SimulationException {
		Scope scope = new Scope();

		Variable<Integer> x = new Variable<>("x");
		new Assignment<>("x", 5, false).evaluate(scope, new TimeCounter());

		Block onTrue = new Block(new ArrayList<>(List.of(
						new Assignment<>("x",
										TwoArgOperator.newAdd(x.evaluate(scope, new TimeCounter()), new Value<>(1)),
										false)
		)));
		Block onFalse = new Block(new ArrayList<>(List.of(
						new Assignment<>("x",
										TwoArgOperator.newAdd(x.evaluate(scope, new TimeCounter()), new Value<>(-1)),
										false)
		)));

		Value<Boolean> cond = TwoArgOperator.newEqual(
						TwoArgOperator.newModulo(
										scope.getVariable("x"),
										new Value<>(2)),
						new Value<>(0)
		).evaluate(scope, new TimeCounter());

		// `if (x % 2 == 0) {x++;} else {x--;}`
		new If(cond, onTrue, onFalse).evaluate(scope, new TimeCounter());

		assertEquals(4, scope.getVariable("x").getValue());
	}

	@Test
	public void testSimpleWhile() throws SimulationException {
		Scope scope = new Scope();

		Variable<Integer> x = new Variable<>("x");
		Variable<Integer> count = new Variable<>("count");
		new Assignment<>("x", 0, false).evaluate(scope, new TimeCounter());
		new Assignment<>("count", 420, false).evaluate(scope, new TimeCounter());

		// Note: don't hard-code evaluations in operators unless you want endless loops!
		// - write `x` instead of `x.evaluate(scope)`
		// - write `scope.getVariable("x")` instead of `scope.getVariable("x").getValue()`

		var cond = TwoArgOperator.newGreater(
						count,
						new Value<>(0)
		);

		Block block = new Block(new ArrayList<>(List.of(
						new Assignment<>("x",
										TwoArgOperator.newAdd(x, new Value<>(1)),
										false),
						new Assignment<>("count",
										TwoArgOperator.newSubtract(count, new Value<>(1)),
										false)
		)));

		// `while (count > 0) {x++; count--}`
		new While(cond, block).evaluate(scope, new TimeCounter());

		assertEquals(420, x.evaluate(scope, new TimeCounter()).getValue());
	}

	@Test
	public void testInnerScopeAssignment() throws SimulationException {
		Scope outerScope = new Scope();
		Scope innerScope = new Scope(outerScope);

		Variable<Integer> x = new Variable<>("x");
		new Assignment<>("x", 1, false).evaluate(outerScope, new TimeCounter());
		new Assignment<>("y", x.evaluate(innerScope, new TimeCounter()), false).evaluate(innerScope, new TimeCounter());
		new Assignment<>("x",
						TwoArgOperator.newAdd(x.evaluate(innerScope, new TimeCounter()), new Value<>(1)),
						false
		).evaluate(innerScope, new TimeCounter());

		assertEquals(2, innerScope.getVariable("x").getValue());
		assertEquals(2, outerScope.getVariable("x").getValue());
		assertEquals(1, innerScope.getVariable("y").getValue());
	}

	@Test
	public void testProgramFib() throws SimulationException {
		final int N = 20;
		Scope scope = new Scope();

		Variable<Integer> f1 = new Variable<>("f1", scope, 0);
		Variable<Integer> f2 = new Variable<>("f2", scope, 1);
		Variable<Integer> f3 = new Variable<>("f3", scope);
		Variable<Integer> n = new Variable<>("n", scope, N);
		Variable<Integer> i = new Variable<>("i", scope, 0);

		var whileCond = TwoArgOperator.newLessEqual(i, n);
		var whileBlock = new Block(
						new Assignment<>("f3", TwoArgOperator.newAdd(f1, f2), false),
						new Assignment<>("f1", f2, false),
						new Assignment<>("f2", f3, false),
						new Assignment<>("i", TwoArgOperator.newAdd(i, new Value<>(1)), false)
		);

		new Block(
						new Assignment<>("n", TwoArgOperator.newSubtract(n, new Value<>(3)), false),
						new While(whileCond, whileBlock)
		).evaluate(scope, new TimeCounter());

		assertEquals(4181, f3.evaluate(scope, new TimeCounter()).getValue());
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
			Variable<Integer> x = new Variable<>("x");
			Variable<Integer> y = new Variable<>("y");
			InputHandler inputHandler = new InputHandler(pkg);
			OutputHandler outputHandler = new OutputHandler(pkg);

			inputHandler.takeVariables(x, y);
			inputHandler.evaluate(scope, new TimeCounter());

			Value<Integer> ret = TwoArgOperator.newAdd(x, y).evaluate(scope, new TimeCounter());
			outputHandler.takeValues(ret);
			outputHandler.evaluate(scope, new TimeCounter());

			assertTrue(pkg.checkTest(123));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
