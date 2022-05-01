package mimuw.idlearn.language.keywords;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.TimeCounter;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.language.exceptions.SimulationException;

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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;

		Assignment<T> other = (Assignment<T>) o;

		return name.equals(other.name) && expression.equals(other.expression);
	}


	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (expression != null ? expression.hashCode() : 0);
		return result;
	}
}
