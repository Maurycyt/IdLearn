package mimuw.idlearn.idlang.logic.conversion;

import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.TimeCounter;
import mimuw.idlearn.idlang.logic.base.Value;
import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;

public class IntToBool implements Expression<Boolean> {

	private final Expression<Integer> expression;

	public IntToBool(Expression<Integer> expression) {
		this.expression = expression;
	}

	@Override
	public Value<Boolean> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
		Value<Integer> eval = expression.evaluate(scope, counter);

		return new Value<>(eval.getValue() != 0);
	}
}
