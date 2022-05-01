package mimuw.idlearn.language;

import mimuw.idlearn.language.base.*;
import mimuw.idlearn.language.conversion.BoolToInt;
import mimuw.idlearn.language.conversion.IntToBool;
import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.language.exceptions.EndOfInputException;
import mimuw.idlearn.language.exceptions.SimulationException;
import mimuw.idlearn.language.exceptions.TimeoutException;
import mimuw.idlearn.language.exceptions.UndefinedException;
import mimuw.idlearn.language.keywords.Assignment;
import mimuw.idlearn.language.keywords.Block;
import mimuw.idlearn.language.keywords.If;
import mimuw.idlearn.language.keywords.While;
import mimuw.idlearn.language.operators.OneArgOperator;
import mimuw.idlearn.language.operators.TwoArgOperator;
import mimuw.idlearn.problems.PackageManager;
import mimuw.idlearn.problems.ProblemPackage;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static mimuw.idlearn.language.LanguageTest.OperatorTestConfig.OpName.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;


public class LanguageTest {

	public static class OperatorTestConfig<T> {
		private final T expected;
		private final Expression<T> op;
		private final OpName opName;
		private final int id;

		public enum OpName {
			Add,
			Sub,
			Mul,
			Div,
			And,
			Or,
			Not,
			Less,
			Leq,
			Greater,
			Geq,
			Equ,
			Neq
		}

		@Override
		public String toString() {
			return opName.toString() + "Test" + id;
		}

		public OperatorTestConfig(T expected, Expression<T> op, OpName opName, int id) {
			this.expected = expected;
			this.op = op;
			this.opName = opName;
			this.id = id;
		}

		public void checkResult(Scope scope) throws SimulationException {
			assertEquals(expected, op.evaluate(scope, new TimeCounter()).getValue());
		}
	}

	public Collection<DynamicTest> simpleValueArithmeticTests() {
		List<DynamicTest> dynamicTests = new ArrayList<>();

		Scope scope = new Scope();
		Value<Integer> val1 = new Value<>(21);
		Value<Integer> val2 = new Value<>(3);

		ArrayList<OperatorTestConfig<Integer>> arr = new ArrayList<>(Arrays.asList(
				new OperatorTestConfig<>(24, TwoArgOperator.newAdd(val1, val2), Add, 0),
				new OperatorTestConfig<>(18, TwoArgOperator.newSubtract(val1, val2), Sub, 0),
				new OperatorTestConfig<>(63, TwoArgOperator.newMultiply(val1, val2), Mul, 0),
				new OperatorTestConfig<>(7, TwoArgOperator.newDivide(val1, val2), Div, 0)
		));

		arr.forEach(opTest -> dynamicTests.add(
				dynamicTest("simpleValue" + opTest.toString(),
						() -> opTest.checkResult(scope))));

		return dynamicTests;
	}

	@Test
	public void testSimpleValueArithmetic() throws Throwable {
		Collection<DynamicTest> tests = simpleValueArithmeticTests();
		for (DynamicTest test : tests) {
			test.getExecutable().execute();
		}
	}

	public Collection<DynamicTest> simpleValueLogicTests() {
		List<DynamicTest> dynamicTests = new ArrayList<>();

		Scope scope = new Scope();
		Value<Boolean> _true = new Value<>(true);
		Value<Boolean> _false = new Value<>(false);
		Value<Integer> x1 = new Value<>(5);
		Value<Integer> x2 = new Value<>(100);

		ArrayList<OperatorTestConfig<Boolean>> arr = new ArrayList<>(Arrays.asList(
				new OperatorTestConfig<>(true, TwoArgOperator.newOr(_true, _false), Or, 0),
				new OperatorTestConfig<>(false, TwoArgOperator.newAnd(_true, _false), And, 0),
				new OperatorTestConfig<>(false, OneArgOperator.newNot(_true), Not, 0),
				new OperatorTestConfig<>(true, OneArgOperator.newNot(_false), Not, 0),
				new OperatorTestConfig<>(true, TwoArgOperator.newLessEqual(x1, x2), Leq, 0),
				new OperatorTestConfig<>(true, TwoArgOperator.newLess(x1, x2), Less, 0),
				new OperatorTestConfig<>(false, TwoArgOperator.newGreater(x1, x2), Greater, 0),
				new OperatorTestConfig<>(false, TwoArgOperator.newGreaterEqual(x1, x2), Geq, 0),
				new OperatorTestConfig<>(false, TwoArgOperator.newEqual(x1, x2), Equ, 0),
				new OperatorTestConfig<>(true, TwoArgOperator.newNotEqual(x1, x2), Neq, 0)
		));

		arr.forEach(opTest -> dynamicTests.add(
				dynamicTest("simpleValue" + opTest.toString(),
						() -> opTest.checkResult(scope))));

		return dynamicTests;
	}

	@Test
	public void testSimpleValueLogic() throws Throwable {
		Collection<DynamicTest> tests = simpleValueLogicTests();
		for (DynamicTest test : tests) {
			test.getExecutable().execute();
		}
	}

	public Collection<DynamicTest> simpleNestedArithmeticTests() {
		List<DynamicTest> dynamicTests = new ArrayList<>();

		Scope scope = new Scope();

		ArrayList<OperatorTestConfig<Integer>> arr = new ArrayList<>(Arrays.asList(
				new OperatorTestConfig<>(70,
						TwoArgOperator.newAdd(new Value<>(35),
								TwoArgOperator.newAdd(new Value<>(20), new Value<>(15))),
						Add, 0),
				new OperatorTestConfig<>(70,
						TwoArgOperator.newSubtract(new Value<>(140),
								TwoArgOperator.newSubtract(new Value<>(300), new Value<>(230))),
						Sub, 0),
				new OperatorTestConfig<>(70,
						TwoArgOperator.newMultiply(new Value<>(5),
								TwoArgOperator.newMultiply(new Value<>(7), new Value<>(2))),
						Mul, 0),
				new OperatorTestConfig<>(70,
						TwoArgOperator.newDivide(new Value<>(1400),
								TwoArgOperator.newDivide(new Value<>(40), new Value<>(2))),
						Div, 0),
				new OperatorTestConfig<>(40445,
						TwoArgOperator.newAdd(
								new Value<>(1500),
								TwoArgOperator.newSubtract(
										new Value<>(100000),
										TwoArgOperator.newMultiply(
												new Value<>(5),
												TwoArgOperator.newDivide(
														new Value<>(244220),
														new Value<>(20)
												)
										)
								)
						),
						Add, 1)
		));

		arr.forEach(opTest -> dynamicTests.add(
				dynamicTest("nested" + opTest.toString(),
						() -> opTest.checkResult(scope))));

		return dynamicTests;
	}

	@Test
	public void testSimpleNestedArithmetic() throws Throwable {
		Collection<DynamicTest> tests = simpleNestedArithmeticTests();
		for (DynamicTest test : tests) {
			test.getExecutable().execute();
		}
	}

	public Collection<DynamicTest> simpleNestedLogicTests() {
		List<DynamicTest> dynamicTests = new ArrayList<>();

		Scope scope = new Scope();

		ArrayList<OperatorTestConfig<Boolean>> arr = new ArrayList<>(Arrays.asList(
				new OperatorTestConfig<>(false,
						TwoArgOperator.newAnd(new Value<>(true),
								TwoArgOperator.newAnd(new Value<>(true), new Value<>(false))),
						And, 0),
				new OperatorTestConfig<>(true,
						TwoArgOperator.newOr(new Value<>(false),
								TwoArgOperator.newOr(new Value<>(true), new Value<>(false))),
						Or, 0),
				new OperatorTestConfig<>(false,
						OneArgOperator.newNot(
								OneArgOperator.newNot(new Value<>(false))),
						Not, 0),
				new OperatorTestConfig<>(true,
						OneArgOperator.newNot(
								TwoArgOperator.newAnd(
										new Value<>(true),
										TwoArgOperator.newOr(
												new Value<>(false),
												new Value<>(false)
										)
								)),
						Not, 1)
		));

		arr.forEach(opTest -> dynamicTests.add(
				dynamicTest("nested" + opTest.toString(),
						() -> opTest.checkResult(scope))));

		return dynamicTests;
	}

	@Test
	public void testSimpleNestedLogic() throws Throwable {
		Collection<DynamicTest> tests = simpleNestedLogicTests();
		for (DynamicTest test : tests) {
			test.getExecutable().execute();
		}
	}

	@Test
	public void testBoolIntConversion() throws SimulationException {
		Scope scope = new Scope();

		new Block(
						new Assignment<>("a1", new IntToBool(new Value<>(0)), false),
						new Assignment<>("a2", new IntToBool(new Value<>(1)), false),
						new Assignment<>("a3", new IntToBool(new Value<>(-5)), false),

						new Assignment<>("b1", new BoolToInt(new Value<>(false)), false),
						new Assignment<>("b2", new BoolToInt(new Value<>(true)), false)
		).evaluate(scope, new TimeCounter());

		assertEquals(false, scope.getVariable("a1").getValue());
		assertEquals(true, scope.getVariable("a2").getValue());
		assertEquals(true, scope.getVariable("a3").getValue());

		assertEquals(0, scope.getVariable("b1").getValue());
		assertEquals(1, scope.getVariable("b2").getValue());
	}

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
	public void testArithmeticOperationsEfficiency() throws SimulationException {
		final int N = (int)(TimeCounter.MAX_TIME / 5);
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

		long start = System.currentTimeMillis();
		outerBlock.evaluate(scope, new TimeCounter());
		long end = System.currentTimeMillis();

		System.out.println(2*N + " operations performed in " + (end - start) + " milliseconds.");
		assertTrue(end - start < 10000);
	}

	@Test
	public void testTimeLimit() {
		final int N = (int)(TimeCounter.MAX_TIME);
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
	public void testInputAndOutput() throws SimulationException {
		try {
			ProblemPackage pkg = PackageManager.getProblemPackage("Addition");
			pkg.prepareTest(123);

			Scope scope = new Scope();
			Variable<Integer> x = new Variable<>("x");
			Variable<Integer> y = new Variable<>("y");
			Variable<Integer> z = new Variable<>("z");
			InputHandler inputHandler = new InputHandler(pkg);
			OutputHandler outputHandler = new OutputHandler(pkg);

			inputHandler.takeVariables(x, y, z);
			assertThrows(EndOfInputException.class, () -> inputHandler.evaluate(scope, new TimeCounter()));
			pkg.resetScanner();

			inputHandler.takeVariables(x, y);
			inputHandler.evaluate(scope, new TimeCounter());

			Value<Integer> ret = TwoArgOperator.newAdd(x, y).evaluate(scope, new TimeCounter());
			outputHandler.takeValues(ret);
			outputHandler.evaluate(scope, new TimeCounter());

			assert(pkg.checkTest());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

