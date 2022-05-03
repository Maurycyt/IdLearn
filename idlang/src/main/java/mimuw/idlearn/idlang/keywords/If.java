package mimuw.idlearn.idlang.keywords;

import mimuw.idlearn.idlang.base.Expression;
import mimuw.idlearn.idlang.base.TimeCounter;
import mimuw.idlearn.idlang.base.Value;
import mimuw.idlearn.idlang.environment.Scope;
import mimuw.idlearn.idlang.exceptions.SimulationException;

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
	public Value<Void> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
		Boolean condEvaluation = condition.evaluate(scope, counter).getValue();
		counter.addTime(delay);

		if (condEvaluation)
			return onTrue.evaluate(new Scope(scope), counter);
		if (onFalse != null)
			return onFalse.evaluate(new Scope(scope), counter);
		return new Value<>(null);
	}
}
