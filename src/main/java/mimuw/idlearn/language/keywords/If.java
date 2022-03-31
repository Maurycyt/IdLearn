package mimuw.idlearn.language.keywords;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;

public class If<Void> implements Expression<Void> {
	private final Expression<Boolean> condition;
	private final Block<Void> onTrue;
	private final Block<Void> onFalse;

	public If(Expression<Boolean> condition, Block<Void> onTrue, Block<Void> onFalse) {
		this.condition = condition;
		this.onTrue = onTrue;
		this.onFalse = onFalse;
	}

	public If(Expression<Boolean> condition, Block<Void> onTrue) {
		this.condition = condition;
		this.onTrue = onTrue;
		this.onFalse = null;
	}

	@Override
	public Value<Void> evaluate(Scope scope) throws RuntimeException{
		Boolean condEvaluation = condition.evaluate(scope).getValue();

		if ((Boolean) condEvaluation)
			return onTrue.evaluate(new Scope(scope));
		if (onFalse != null)
			return onFalse.evaluate(new Scope(scope));
		return new Value<>(null);
	}
	
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		
		If<Void> other = (If<Void>)o;

		return condition.equals(other.condition) && onTrue.equals(other.onTrue) && onFalse.equals(other.onFalse);
	}

	@Override
	public int hashCode(){
		int result = super.hashCode();
		result = 31 * result + (condition != null ? condition.hashCode() : 0);
		result = 31 * result + (onTrue != null ? onTrue.hashCode() : 0);
		result = 31 * result + (onFalse != null ? onFalse.hashCode() : 0);
		return result;
	}

}
