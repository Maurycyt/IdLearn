package mimuw.idlearn.language.conversion;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.TimeCounter;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;

public class IntToBool implements Expression<Boolean> {

	private final Expression<Integer> expression;

	public IntToBool(Expression<Integer> expression) {
		this.expression = expression;
	}

	@Override
	public Value<Boolean> evaluate(Scope scope, TimeCounter counter) throws RuntimeException {
		Value<Integer> eval = expression.evaluate(scope, counter);

		if (eval.getValue() != 0) {
			return new Value<>(true);
		} else {
			return new Value<>(false);
		}
	}
}
