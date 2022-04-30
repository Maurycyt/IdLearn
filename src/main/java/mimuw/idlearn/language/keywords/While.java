package mimuw.idlearn.language.keywords;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;

public class While implements Expression<Void> {
	private final Expression<Boolean> condition;
	private final Block body;

	public While(Expression<Boolean> condition, Block body) {
		this.condition = condition;
		this.body = body;
	}

	@Override
	public Value<Void> evaluate(Scope scope) throws RuntimeException {
		while (condition.evaluate(scope).getValue()) {
			body.evaluate(scope);
		}
		return new Value<>(null);
	}
}
