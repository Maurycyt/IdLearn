package mimuw.idlearn.idlang.logic;

import mimuw.idlearn.idlang.logic.base.Constant;
import mimuw.idlearn.idlang.logic.base.TimeCounter;
import mimuw.idlearn.idlang.logic.base.Variable;
import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;
import mimuw.idlearn.idlang.logic.keywords.Assignment;
import mimuw.idlearn.idlang.logic.keywords.Block;
import mimuw.idlearn.idlang.logic.keywords.While;
import mimuw.idlearn.idlang.logic.operators.TwoArgOperator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EfficiencyTest {
	@Test
	public void testArithmeticOperationsEfficiency() throws SimulationException {
		final long N = (int)(TimeCounter.MAX_TIME / 5);
		Scope scope = new Scope();

		Variable i = new Variable("i", scope, N);
		Constant zero = new Constant(0);

		var whileCond = TwoArgOperator.newGreaterEqual(i, zero);
		var whileBlock = new Block(
						new Assignment("i",
										TwoArgOperator.newSubtract(i, new Constant(1)),
										false
						)
		);
		var outerBlock = new Block(
						new While(whileCond, whileBlock)
		);

		long start = System.currentTimeMillis();
		outerBlock.evaluate(scope, new TimeCounter(), null, null);
		long end = System.currentTimeMillis();

		System.out.println(3 * N + " operations performed in " + (end - start) + " milliseconds.");
		assertTrue(end - start < 10000);
	}
}
