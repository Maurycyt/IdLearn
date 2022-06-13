package mimuw.idlearn.idlang.logic;

import mimuw.idlearn.idlang.logic.base.*;
import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;
import mimuw.idlearn.idlang.logic.operators.OneArgOperator;
import mimuw.idlearn.idlang.logic.operators.TwoArgOperator;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class ComputationTest {
	public static class OperatorTestConfig {
		private final Object expected;
		private final Expression op;

		public OperatorTestConfig(Object expected, Expression op) {
			this.expected = expected;
			this.op = op;
		}

		public void checkResult(Scope scope) throws SimulationException {
			assertEquals(expected, op.evaluate(scope, new ResourceCounter(), null, null).value);
		}
	}

	public Collection<DynamicTest> simpleValueArithmeticTests() {
		List<DynamicTest> dynamicTests = new ArrayList<>();

		Scope scope = new Scope();
		Constant val1 = new Constant(new Value(21));
		Constant val2 = new Constant(new Value(3));

		ArrayList<OperatorTestConfig> arr = new ArrayList<>(Arrays.asList(
						new OperatorTestConfig(24L, TwoArgOperator.newAdd(val1, val2)),
						new OperatorTestConfig(18L, TwoArgOperator.newSubtract(val1, val2)),
						new OperatorTestConfig(63L, TwoArgOperator.newMultiply(val1, val2)),
						new OperatorTestConfig(7L, TwoArgOperator.newDivide(val1, val2))
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
		Constant _1 = new Constant(new Value(1));
		Constant _0 = new Constant(new Value(0));
		Constant x1 = new Constant(new Value(5));
		Constant x2 = new Constant(new Value(100));

		ArrayList<OperatorTestConfig> arr = new ArrayList<>(Arrays.asList(
						new OperatorTestConfig(1L, TwoArgOperator.newOr(_1, _0)),
						new OperatorTestConfig(0L, TwoArgOperator.newAnd(_1, _0)),
						new OperatorTestConfig(0L, OneArgOperator.newNot(_1)),
						new OperatorTestConfig(1L, OneArgOperator.newNot(_0)),
						new OperatorTestConfig(1L, TwoArgOperator.newLessEqual(x1, x2)),
						new OperatorTestConfig(1L, TwoArgOperator.newLess(x1, x2)),
						new OperatorTestConfig(0L, TwoArgOperator.newGreater(x1, x2)),
						new OperatorTestConfig(0L, TwoArgOperator.newGreaterEqual(x1, x2)),
						new OperatorTestConfig(0L, TwoArgOperator.newEqual(x1, x2)),
						new OperatorTestConfig(1L, TwoArgOperator.newNotEqual(x1, x2))
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

		ArrayList<OperatorTestConfig> arr = new ArrayList<>(Arrays.asList(
						new OperatorTestConfig(70L,
										TwoArgOperator.newAdd(new Constant(new Value(35)),
														TwoArgOperator.newAdd(new Constant(new Value(20)), new Constant(new Value(15))))
						),
						new OperatorTestConfig(70L,
										TwoArgOperator.newSubtract(new Constant(new Value(140)),
														TwoArgOperator.newSubtract(new Constant(new Value(230)), new Constant(new Value(160))))
						),
						new OperatorTestConfig(70L,
										TwoArgOperator.newMultiply(new Constant(new Value(5)),
														TwoArgOperator.newMultiply(new Constant(new Value(7)), new Constant(new Value(2))))
						),
						new OperatorTestConfig(70L,
										TwoArgOperator.newDivide(new Constant(new Value(1400)),
														TwoArgOperator.newDivide(new Constant(new Value(40)), new Constant(new Value(2))))
						),
						new OperatorTestConfig(40445L,
										TwoArgOperator.newAdd(
														new Constant(new Value(1500)),
														TwoArgOperator.newSubtract(
																		new Constant(new Value(100000)),
																		TwoArgOperator.newMultiply(
																						new Constant(new Value(5)),
																						TwoArgOperator.newDivide(
																										new Constant(new Value(244220)),
																										new Constant(new Value(20))
																						)
																		)
														)
										)
						)
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

		ArrayList<OperatorTestConfig> arr = new ArrayList<>(Arrays.asList(
						new OperatorTestConfig(0L,
										TwoArgOperator.newAnd(new Constant(new Value(1)),
														TwoArgOperator.newAnd(new Constant(new Value(1)),
																new Constant(new Value(0))))
						),
						new OperatorTestConfig(1L,
										TwoArgOperator.newOr(new Constant(new Value(0)),
														TwoArgOperator.newOr(new Constant(new Value(1)), new Constant(new Value(0))))
						),
						new OperatorTestConfig(0L,
										OneArgOperator.newNot(
														OneArgOperator.newNot(new Constant(new Value(0))))
						),
						new OperatorTestConfig(1L,
										OneArgOperator.newNot(
														TwoArgOperator.newAnd(
																		new Constant(new Value(1)),
																		TwoArgOperator.newOr(
																						new Constant(new Value(0)),
																						new Constant(new Value(0))
																		)
														))
						)
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

}