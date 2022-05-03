package mimuw.idlearn.idlang;

import mimuw.idlearn.idlang.base.Expression;
import mimuw.idlearn.idlang.base.TimeCounter;
import mimuw.idlearn.idlang.base.Value;
import mimuw.idlearn.idlang.conversion.BoolToInt;
import mimuw.idlearn.idlang.conversion.IntToBool;
import mimuw.idlearn.idlang.environment.Scope;
import mimuw.idlearn.idlang.exceptions.SimulationException;
import mimuw.idlearn.idlang.keywords.Assignment;
import mimuw.idlearn.idlang.keywords.Block;
import mimuw.idlearn.idlang.operators.OneArgOperator;
import mimuw.idlearn.idlang.operators.TwoArgOperator;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static mimuw.idlearn.idlang.ComputationTest.OperatorTestConfig.OpName.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class ComputationTest {
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
}