package mimuw.idlearn.language.keywords;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.TimeCounter;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.language.exceptions.SimulationException;

public class While implements Expression<Void> {
	private final Expression<Boolean> condition;
	private final Block body;

	public While(Expression<Boolean> condition, Block body) {
		this.condition = condition;
		this.body = body;
	}

	@Override
	public Value<Void> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
		while (condition.evaluate(scope, counter).getValue()) {
			counter.addTime(delay);
			body.evaluate(scope, counter);
		}
		return new Value<>(null);
	}
}
