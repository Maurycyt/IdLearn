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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;

		While other = (While) o;

		return condition.equals(other.condition) && body.equals(other.body);
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (condition != null ? condition.hashCode() : 0);
		result = 31 * result + (body != null ? body.hashCode() : 0);
		return result;
	}

}
