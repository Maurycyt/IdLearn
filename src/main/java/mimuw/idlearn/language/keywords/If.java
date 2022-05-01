package mimuw.idlearn.language.keywords;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.TimeCounter;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.language.exceptions.SimulationException;

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
	public Value<Void> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
		Boolean condEvaluation = condition.evaluate(scope, counter).getValue();
		counter.addTime(delay);

		if (condEvaluation)
			return onTrue.evaluate(new Scope(scope), counter);
		if (onFalse != null)
			return onFalse.evaluate(new Scope(scope), counter);
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
