package mimuw.idlearn.language;

import mimuw.idlearn.language.base.*;
import mimuw.idlearn.language.environment.*;
import mimuw.idlearn.language.keywords.Assignment;
import mimuw.idlearn.language.operators.OneArgumentOperator;
import mimuw.idlearn.language.operators.Operator;
import mimuw.idlearn.language.operators.TwoArgumentOperator;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static mimuw.idlearn.language.LanguageTest.OperatorTestConfig.OpName.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;


public class LanguageTest {

    public class OperatorTestConfig<T> {
        private final T expected;
        private final Operator<T> op;
        private final OpName opName;
        private final int id;
        public enum OpName {
            Add,
            Sub,
            Mul,
            Div,
            And,
            Or,
            Not
        }

        @Override
        public String toString() {
            return opName.toString() + "Test" + id;
        }

        public OperatorTestConfig(T expected, Operator<T> op, OpName opName, int id) {
            this.expected = expected;
            this.op = op;
            this.opName = opName;
            this.id = id;
        }

        public void checkResult(Scope scope) {
            assertEquals(expected, op.evaluate(scope).getValue());
        }
    }

    @TestFactory
    Collection<DynamicTest> simpleValueArithmeticTests() {
        List<DynamicTest> dynamicTests = new ArrayList<>();

        Scope globalScope = new Scope();
        Scope scope = new Scope(globalScope);
        Value<Integer> val1 = new Value<>(21);
        Value<Integer> val2 = new Value<>(3);

        ArrayList<OperatorTestConfig<Integer>> arr = new ArrayList<>(Arrays.asList(
                new OperatorTestConfig<>(24, TwoArgumentOperator.newAdd(val1, val2), Add, 0),
                new OperatorTestConfig<>(18, TwoArgumentOperator.newSub(val1, val2), Sub, 0),
                new OperatorTestConfig<>(63, TwoArgumentOperator.newMul(val1, val2), Mul, 0),
                new OperatorTestConfig<>(7, TwoArgumentOperator.newDiv(val1, val2), Div, 0)
        ));

        arr.forEach(opTest -> dynamicTests.add(
                dynamicTest("simpleValue" + opTest.toString(),
                        () -> opTest.checkResult(scope))));

        return dynamicTests;
    }

    @TestFactory
    Collection<DynamicTest> simpleValueLogicTests() {
        List<DynamicTest> dynamicTests = new ArrayList<>();

        Scope globalScope = new Scope();
        Scope scope = new Scope(globalScope);
        Value<Boolean> _true = new Value<>(true);
        Value<Boolean> _false = new Value<>(false);

        ArrayList<OperatorTestConfig<Boolean>> arr = new ArrayList<>(Arrays.asList(
                new OperatorTestConfig<>(true, TwoArgumentOperator.newOr(_true, _false), Or, 0),
                new OperatorTestConfig<>(false, TwoArgumentOperator.newAnd(_true, _false), And, 0),
                new OperatorTestConfig<>(false, OneArgumentOperator.newNot(_true), Not, 0),
                new OperatorTestConfig<>(true, OneArgumentOperator.newNot(_false), Not, 0)
        ));

        arr.forEach(opTest -> dynamicTests.add(
                dynamicTest("simpleValue"+ opTest.toString(),
                        () -> opTest.checkResult(scope))));

        return dynamicTests;
    }

    @TestFactory
    Collection<DynamicTest> simpleNestedArithmeticTests() {
        List<DynamicTest> dynamicTests = new ArrayList<>();

        Scope globalScope = new Scope();
        Scope scope = new Scope(globalScope);

        ArrayList<OperatorTestConfig<Integer>> arr = new ArrayList<>(Arrays.asList(
                new OperatorTestConfig<>(70,
                        TwoArgumentOperator.newAdd(new Value<>(35),
                                TwoArgumentOperator.newAdd(new Value<>(20), new Value<>(15))),
                        Add, 0),
                new OperatorTestConfig<>(70,
                        TwoArgumentOperator.newSub(new Value<>(140),
                                TwoArgumentOperator.newSub(new Value<>(300), new Value<>(230))),
                        Sub, 0),
                new OperatorTestConfig<>(70,
                        TwoArgumentOperator.newMul(new Value<>(5),
                                TwoArgumentOperator.newMul(new Value<>(7), new Value<>(2))),
                        Mul, 0),
                new OperatorTestConfig<>(70,
                        TwoArgumentOperator.newDiv(new Value<>(1400),
                                TwoArgumentOperator.newDiv(new Value<>(40), new Value<>(2))),
                        Div, 0),
                new OperatorTestConfig<>(40445,
                        TwoArgumentOperator.newAdd(
                                new Value<>(1500),
                                TwoArgumentOperator.newSub(
                                        new Value<>(100000),
                                        TwoArgumentOperator.newMul(
                                                new Value<>(5),
                                                TwoArgumentOperator.newDiv(
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

    @TestFactory
    Collection<DynamicTest> simpleNestedLogicTests() {
        List<DynamicTest> dynamicTests = new ArrayList<>();

        Scope globalScope = new Scope();
        Scope scope = new Scope(globalScope);

        ArrayList<OperatorTestConfig<Boolean>> arr = new ArrayList<>(Arrays.asList(
                new OperatorTestConfig<>(false,
                        TwoArgumentOperator.newAnd(new Value<>(true),
                                TwoArgumentOperator.newAnd(new Value<>(true), new Value<>(false))),
                        And, 0),
                new OperatorTestConfig<>(true,
                        TwoArgumentOperator.newOr(new Value<>(false),
                                TwoArgumentOperator.newOr(new Value<>(true), new Value<>(false))),
                        Or, 0),
                new OperatorTestConfig<>(false,
                        OneArgumentOperator.newNot(
                                OneArgumentOperator.newNot(new Value<>(false))),
                        Not, 0),
                new OperatorTestConfig<>(true,
                        OneArgumentOperator.newNot(
                                TwoArgumentOperator.newAnd(
                                        new Value<>(true),
                                        TwoArgumentOperator.newOr(
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
    public void simpleIfTest() {

    }

    @Test
    public void simpleIfElseTest() {

    }

    @Test
    public void simpleWhileTest() {
        Scope globalScope = new Scope();
        Scope scope = new Scope(globalScope);
    }

    @Test
    public void innerScopeAssignmentTest() {
        Scope globalScope = new Scope();
        Scope outerScope = new Scope(globalScope);
        Scope innerScope = new Scope(outerScope);

        Variable<Integer> x = new Variable<>("x");
        Value<Integer> asn1 = new Assignment<>("x", 1).evaluate(outerScope);
        Value<Integer> asn2 = new Assignment<>("y", x.evaluate(innerScope)).evaluate(innerScope);
        Value<Integer> asn3 =  new Assignment<>("x",  x.evaluate(innerScope).getValue() + 1).evaluate(innerScope);

        assertEquals(innerScope.getVariable("x").getValue(), 2);
        assertEquals(outerScope.getVariable("x").getValue(), 2);
        assertEquals(innerScope.getVariable("y").getValue(), 1);
    }

    @Test
    public void programFibTest() {

    }

    @Test
    public void programBinSearchTest() {

    }

}

