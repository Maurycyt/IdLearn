package mimuw.idlearn.language.keywords;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;

import java.util.Objects;

public class If implements Expression<Void> {
	private final Expression<Boolean> condition;
	private final Block onTrue;
	private final Block onFalse;

	public If(Expression<Boolean> condition, Block onTrue, Block onFalse) {
		this.condition = condition;
		this.onTrue = onTrue;
		this.onFalse = onFalse;
	}

	public If(Expression<Boolean> condition, Block onTrue) {
		this.condition = condition;
		this.onTrue = onTrue;
		this.onFalse = null;
	}

	@Override
	public Value<Void> evaluate(Scope scope) throws RuntimeException {
		Boolean condEvaluation = condition.evaluate(scope).getValue();

		if (condEvaluation)
			return onTrue.evaluate(new Scope(scope));
		if (onFalse != null)
			return onFalse.evaluate(new Scope(scope));
		return new Value<>(null);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		If other = (If) o;

		if (!Objects.equals(condition, other.condition)) return false;
		if (!Objects.equals(onTrue, other.onTrue)) return false;
		return Objects.equals(onFalse, other.onFalse);
	}

	@Override
	public int hashCode() {
		int result = condition != null ? condition.hashCode() : 0;
		result = 31 * result + (onTrue != null ? onTrue.hashCode() : 0);
		result = 31 * result + (onFalse != null ? onFalse.hashCode() : 0);
		return result;
	}
}
