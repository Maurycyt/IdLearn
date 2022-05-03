package mimuw.idlearn.idlang.conversion;

import mimuw.idlearn.idlang.base.Expression;
import mimuw.idlearn.idlang.base.TimeCounter;
import mimuw.idlearn.idlang.base.Value;
import mimuw.idlearn.idlang.environment.Scope;
import mimuw.idlearn.idlang.exceptions.SimulationException;

public class BoolToInt implements Expression<Integer> {

	private final Expression<Boolean> expression;

	public BoolToInt(Expression<Boolean> expression) {
		this.expression = expression;
	}

	@Override
	public Value<Integer> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
		Value<Boolean> eval = expression.evaluate(scope, counter);

		return new Value<>(eval.getValue() ? 1 : 0);
	}
}
