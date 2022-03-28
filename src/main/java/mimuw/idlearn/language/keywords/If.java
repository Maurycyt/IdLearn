package mimuw.idlearn.language.keywords;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.language.operators.RelationalOperator;

public class If extends Expression {
	private final RelationalOperator condition;
	private final Block onTrue;
	private final Block onFalse;

	public If(RelationalOperator condition, Block onTrue, Block onFalse) {
		this.condition = condition;
		this.onTrue = onTrue;
		this.onFalse = onFalse;
	}

	public If(RelationalOperator condition, Block onTrue) {
		this.condition = condition;
		this.onTrue = onTrue;
		this.onFalse = null;
	}

	@Override
	public Value evaluate(Scope scope) throws RuntimeException{
		Object condEvaluation = condition.evaluate(scope).getValue();

		if ((Boolean) condEvaluation)
			return onTrue.evaluate(new Scope(scope));
		if (onFalse != null)
			return onFalse.evaluate(new Scope(scope));
		return new Value(false);
	}
	
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		
		If other = (If)o;

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

	@Override
	public String toPrettyString(String indent){
		StringBuilder out = new StringBuilder()
			.append("if (")
			.append(condition.toPrettyString(indent + "    "))
			.append(")")
			.append(onTrue.toPrettyString(indent));

		if (onFalse != null) {
			out
				.append("\n")
				.append(indent)
				.append("else")
				.append(onFalse.toPrettyString(indent));
		}

		return out.toString();
	}
}
