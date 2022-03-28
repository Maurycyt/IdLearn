package mimuw.idlearn.language.keywords;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.language.TypeCheck;

public class While extends Expression {
	private final Expression condition;
	private final Expression body;

	public While(Expression condition, Expression body) {
		this.condition = condition;
		this.body = body;
	}

	@Override
	public Value evaluate(Scope scope) throws RuntimeException{
		while (true) {
			Object condEvaluation = condition.evaluate(scope).getValue();
			TypeCheck.assertType(condEvaluation, Boolean.class);
			if (!((Boolean)condEvaluation))
				break;
			body.evaluate(new Scope(scope));
		}
		return new Value(0);
	}
	
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		
		While other = (While)o;

		return condition.equals(other.condition) && body.equals(other.body);
	}

	@Override
	public int hashCode(){
		int result = super.hashCode();
		result = 31 * result + (condition != null ? condition.hashCode() : 0);
		result = 31 * result + (body != null ? body.hashCode() : 0);
		return result;
	}

	@Override
	public String toPrettyString(String indent) {
		StringBuilder out = new StringBuilder()
			.append("while (")
			.append(condition.toPrettyString(indent + tabIndent))
			.append(")");

		if (body.getClass() != Block.class)
			out
				.append("\n")
				.append(indent)
				.append(tabIndent)
				.append(body.toPrettyString(indent + tabIndent));
		else
			out
				.append(body.toPrettyString(indent));
		return out.toString();
	}
}
