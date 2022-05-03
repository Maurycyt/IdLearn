package mimuw.idlearn.idlang;

import mimuw.idlearn.idlang.base.TimeCounter;
import mimuw.idlearn.idlang.base.Value;
import mimuw.idlearn.idlang.base.Variable;
import mimuw.idlearn.idlang.environment.Scope;
import mimuw.idlearn.idlang.exceptions.SimulationException;
import mimuw.idlearn.idlang.keywords.Assignment;
import mimuw.idlearn.idlang.keywords.Block;
import mimuw.idlearn.idlang.keywords.While;
import mimuw.idlearn.idlang.operators.TwoArgOperator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EfficiencyTest {
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

		System.out.println(3 * N + " operations performed in " + (end - start) + " milliseconds.");
		assertTrue(end - start < 10000);
	}
}