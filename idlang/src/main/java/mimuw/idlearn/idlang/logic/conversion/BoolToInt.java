package mimuw.idlearn.idlang.logic.conversion;

import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.TimeCounter;
import mimuw.idlearn.idlang.logic.base.Value;
import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;

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