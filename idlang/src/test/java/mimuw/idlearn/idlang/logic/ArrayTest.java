package mimuw.idlearn.idlang.logic;

import mimuw.idlearn.idlang.logic.base.*;
import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;
import mimuw.idlearn.idlang.logic.keywords.*;
import mimuw.idlearn.idlang.logic.operators.TwoArgOperator;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayTest {
	static final int TAB_SIZE = 10000;

	Expression getSumOfNaturals(long n) {
		return TwoArgOperator.newDivide(TwoArgOperator.newMultiply(new Constant(n), new Constant(n + 1)), new Constant(2));
	}

	Expression getResult(long start, long end) {
		GetArray getStart = new GetArray(new Variable(Type.Table, "sum_pref"), new Constant(TAB_SIZE - start));
		GetArray getEnd = new GetArray(new Variable(Type.Table, "sum_pref"), new Constant(TAB_SIZE - end));

		return TwoArgOperator.newSubtract(getEnd, getStart);
	}

	Expression getExpected(long start, long end) {

		return TwoArgOperator.newSubtract(getSumOfNaturals(end), getSumOfNaturals(start));
	}

	@Test
	public void testArrayOperations() throws SimulationException {

		Assignment loop_counter_assign = new Assignment("loop_counter", new Constant(TAB_SIZE), true);
		Assignment tab_value_assign = new Assignment("tab_value", new Constant(0), true);
		Assignment sum_assign = new Assignment("sum", new Constant(0), true);

		MakeArray array_declaration = new MakeArray("sum_pref", new Constant(TAB_SIZE + 1));

		SetArray fill_array = new SetArray(new Variable(Type.Table, "sum_pref"), new Variable(Type.Long, "loop_counter"), new Variable(Type.Long, "sum"));
		Assignment loop_counter_decrease = new Assignment("loop_counter",
				TwoArgOperator.newSubtract(new Variable(Type.Long, "loop_counter"), new Constant(1)),
				false);
		Assignment tab_value_increase = new Assignment("tab_value",
				TwoArgOperator.newAdd(new Variable(Type.Long, "tab_value"), new Constant(1)),
				false);
		Assignment sum_increase = new Assignment("sum",
				TwoArgOperator.newAdd(new Variable(Type.Long, "sum"), new Variable(Type.Long, "tab_value")),
				false);

		While tab_filler = new While(new Variable(Type.Long, "loop_counter"),
				new Block(fill_array, loop_counter_decrease, tab_value_increase, sum_increase)
		);

		Expression block = new Block(loop_counter_assign, tab_value_assign, sum_assign, array_declaration, tab_filler);

		Scope scope = new Scope();

		block.evaluate(scope, new ResourceCounter(), null, null);

		Random random = new Random();

		for (long i = 0; i < 1000000; i++) {
			int a = random.nextInt(TAB_SIZE);
			int b = random.nextInt(TAB_SIZE - a) + a;
			Expression result = getResult(a, b);
			Expression expected = getExpected(a, b);
			Value equality = TwoArgOperator.newEqual(result, expected).evaluate(scope, new ResourceCounter(), null, null);
			assertEquals(1L, equality.value);
		}

	}
}
