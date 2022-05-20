package mimuw.idlearn.idlang.logic.keywords;

import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.TimeCounter;
import mimuw.idlearn.idlang.logic.base.Value;
import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;

import java.io.Writer;
import java.util.Scanner;

public class If implements Expression<Void> {
	private final Expression<Boolean> condition;
	private final Block onTrue;
	private final Block onFalse;

	public If(Expression<Boolean> condition, Block onTrue, Block onFalse) {
		this.condition = condition;
		this.onTrue = onTrue;
		this.onFalse = onFalse;
	}

	public If(Expression<Boolean> condition, Block onTrue) {
		this.condition = condition;
		this.onTrue = onTrue;
		this.onFalse = null;
	}

	@Override
	public Value<Void> evaluate(Scope scope, TimeCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
		Boolean condEvaluation = condition.evaluate(scope, counter, inputScanner, outputWriter).getValue();
		counter.addTime(delay);

		if (condEvaluation)
			return onTrue.evaluate(new Scope(scope), counter, inputScanner, outputWriter);
		if (onFalse != null)
			return onFalse.evaluate(new Scope(scope), counter, inputScanner, outputWriter);
		return new Value<>(null);
	}
}
