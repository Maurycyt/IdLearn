package mimuw.idlearn.idlang.keywords;

import mimuw.idlearn.idlang.base.Expression;
import mimuw.idlearn.idlang.base.TimeCounter;
import mimuw.idlearn.idlang.base.Value;
import mimuw.idlearn.idlang.environment.Scope;
import mimuw.idlearn.idlang.exceptions.SimulationException;

public class Assignment<T> implements Expression<Void> {
	private final String name;
	private final Expression<T> expression;
	private final boolean shouldTakeTime;

	public Assignment(String name, Expression<T> expression, boolean shouldTakeTime) {
		this.name = name;
		this.expression = expression;
		this.shouldTakeTime = shouldTakeTime;
	}

	public Assignment(String name, T expression, boolean shouldTakeTime) {
		this.name = name;
		this.expression = new Value<>(expression);
		this.shouldTakeTime = shouldTakeTime;
	}

	@Override
	public Value<Void> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
		Value<T> eval = expression.evaluate(scope, counter);

		Scope origin = scope.getOriginScope(name);

		if (shouldTakeTime) {
			counter.addTime(delay);
		}

		if (origin == null)
			scope.add(name, eval);
		else
			origin.add(name, eval);

		return new Value<>(null);
	}
}
