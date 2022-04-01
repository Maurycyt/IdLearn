package mimuw.idlearn.language;

import mimuw.idlearn.language.base.*;
import mimuw.idlearn.language.environment.*;
import mimuw.idlearn.language.keywords.Assignment;
import mimuw.idlearn.language.keywords.Block;
import mimuw.idlearn.language.keywords.If;
import mimuw.idlearn.language.keywords.While;
import mimuw.idlearn.language.operators.OneArgOperator;
import mimuw.idlearn.language.operators.Operator;
import mimuw.idlearn.language.operators.TwoArgOperator;
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
                new OperatorTestConfig<>(24, TwoArgOperator.newAdd(val1, val2), Add, 0),
                new OperatorTestConfig<>(18, TwoArgOperator.newSub(val1, val2), Sub, 0),
                new OperatorTestConfig<>(63, TwoArgOperator.newMul(val1, val2), Mul, 0),
                new OperatorTestConfig<>(7, TwoArgOperator.newDiv(val1, val2), Div, 0)
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
        Value<Integer> x1 = new Value<>(5);
        Value<Integer> x2 = new Value<>(100);

        ArrayList<OperatorTestConfig<Boolean>> arr = new ArrayList<>(Arrays.asList(
                new OperatorTestConfig<>(true, TwoArgOperator.newOr(_true, _false), Or, 0),
                new OperatorTestConfig<>(false, TwoArgOperator.newAnd(_true, _false), And, 0),
                new OperatorTestConfig<>(false, OneArgOperator.newNot(_true), Not, 0),
                new OperatorTestConfig<>(true, OneArgOperator.newNot(_false), Not, 0),
                new OperatorTestConfig<>(true, TwoArgOperator.newLeq(x1, x2), Leq, 0),
                new OperatorTestConfig<>(true, TwoArgOperator.newLess(x1, x2), Less, 0),
                new OperatorTestConfig<>(false, TwoArgOperator.newGreater(x1, x2), Greater, 0),
                new OperatorTestConfig<>(false, TwoArgOperator.newGeq(x1, x2), Geq, 0),
                new OperatorTestConfig<>(false, TwoArgOperator.newEqu(x1, x2), Equ, 0),
                new OperatorTestConfig<>(true, TwoArgOperator.newNeq(x1, x2), Neq, 0)
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
                        TwoArgOperator.newAdd(new Value<>(35),
                                TwoArgOperator.newAdd(new Value<>(20), new Value<>(15))),
                        Add, 0),
                new OperatorTestConfig<>(70,
                        TwoArgOperator.newSub(new Value<>(140),
                                TwoArgOperator.newSub(new Value<>(300), new Value<>(230))),
                        Sub, 0),
                new OperatorTestConfig<>(70,
                        TwoArgOperator.newMul(new Value<>(5),
                                TwoArgOperator.newMul(new Value<>(7), new Value<>(2))),
                        Mul, 0),
                new OperatorTestConfig<>(70,
                        TwoArgOperator.newDiv(new Value<>(1400),
                                TwoArgOperator.newDiv(new Value<>(40), new Value<>(2))),
                        Div, 0),
                new OperatorTestConfig<>(40445,
                        TwoArgOperator.newAdd(
                                new Value<>(1500),
                                TwoArgOperator.newSub(
                                        new Value<>(100000),
                                        TwoArgOperator.newMul(
                                                new Value<>(5),
                                                TwoArgOperator.newDiv(
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
    public void simpleIfElseTest() {
        Scope globalScope = new Scope();
        Scope scope = new Scope(globalScope);

        Variable<Integer> x = new Variable<>("x");
        new Assignment<>("x", 5).evaluate(scope);

        Block<Void> onTrue = new Block<>(new ArrayList<>(List.of(
                new Assignment<>("x",
                        TwoArgOperator.newAdd(x.evaluate(scope), new Value<>(1)))
        )));
        Block<Void> onFalse = new Block<>(new ArrayList<>(List.of(
                new Assignment<>("x",
                        TwoArgOperator.newAdd(x.evaluate(scope), new Value<>(-1)))
        )));

        Value<Boolean> cond = TwoArgOperator.newEqu(
                TwoArgOperator.newMod(
                        scope.getVariable("x"),
                        new Value<>(2)),
                new Value<>(0)
        ).evaluate(scope);

        // `if (x % 2 == 0) {x++;} else {x--;}`
        new If<>(cond, onTrue, onFalse).evaluate(scope);

        assertEquals(4, scope.getVariable("x").getValue());
    }

    @Test
    public void simpleWhileTest() {
        Scope globalScope = new Scope();
        Scope scope = new Scope(globalScope);

        Variable<Integer> x = new Variable<>("x");
        Variable<Integer> count = new Variable<>("count");
        new Assignment<>("x", 0).evaluate(scope);
        new Assignment<>("count", 420).evaluate(scope);

        // Note: don't hard-code evaluations in operators unless you want endless loops!
        // - write `x` instead of `x.evaluate(scope)`
        // - write `scope.getVariable("x")` instead of `scope.getVariable("x").getValue()`

        var cond = TwoArgOperator.newGreater(
                count,
                new Value<>(0)
        );

        Block<Void> block = new Block<>(new ArrayList<>(List.of(
                new Assignment<>("x",
                        TwoArgOperator.newAdd(x, new Value<>(1))),
                new Assignment<>("count",
                        TwoArgOperator.newSub(count, new Value<>(1)))
        )));

        // `while (count > 0) {x++; count--}`
        new While<>(cond, block).evaluate(scope);

        assertEquals(420, x.evaluate(scope).getValue());
    }

    @Test
    public void innerScopeAssignmentTest() {
        Scope globalScope = new Scope();
        Scope outerScope = new Scope(globalScope);
        Scope innerScope = new Scope(outerScope);

        Variable<Integer> x = new Variable<>("x");
        Value<Integer> asn1 = new Assignment<>("x", 1).evaluate(outerScope);
        Value<Integer> asn2 = new Assignment<>("y", x.evaluate(innerScope)).evaluate(innerScope);
        Value<Integer> asn3 =  new Assignment<>("x",
                TwoArgOperator.newAdd(x.evaluate(innerScope), new Value<>(1))
        ).evaluate(innerScope);

        assertEquals(2, innerScope.getVariable("x").getValue());
        assertEquals(2, outerScope.getVariable("x").getValue());
        assertEquals(1, innerScope.getVariable("y").getValue());
    }

    @Test
    public void programFibTest() {
        final int N = 20;
        Scope globalScope = new Scope();
        Scope scope = new Scope(globalScope);

        Variable<Integer> f1 = new Variable<>("f1", scope, 0);
        Variable<Integer> f2 = new Variable<>("f2", scope, 1);
        Variable<Integer> f3 = new Variable<>("f3", scope);
        Variable<Integer> n = new Variable<>("n", scope, N);
        Variable<Integer> i = new Variable<>("i", scope, 0);

        var whileCond = TwoArgOperator.newLeq(i, n);
        var whileBlock = new Block<>(
                new Assignment<>("f3", TwoArgOperator.newAdd(f1, f2)),
                new Assignment<>("f1", f2),
                new Assignment<>("f2", f3),
                new Assignment<>("i", TwoArgOperator.newAdd(i, 1))
        );

        var outerBlock = new Block<>(
                new Assignment<>("n", TwoArgOperator.newSub(n, 3)),
                new While<>(whileCond, whileBlock)
        ).evaluate(scope);

        assertEquals(4181, f3.evaluate(scope).getValue());
    }
}

