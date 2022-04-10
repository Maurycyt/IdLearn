package mimuw.idlearn.language.keywords;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;

public class Assignment<T> implements Expression<Void> {
	private final String name;
	private final Expression<T> expression;

	public Assignment(String name, Expression<T> expression) {
		this.name = name;
		this.expression = expression;
	}

	public Assignment(String name, T expression) {
		this.name = name;
		this.expression = new Value<>(expression);
	}

	@Override
	public Value<Void> evaluate(Scope scope) throws RuntimeException {
		Value<T> eval = expression.evaluate(scope);

		Scope origin = scope.getOriginScope(name);

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
