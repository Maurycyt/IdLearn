package mimuw.idlearn.language.keywords;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;

public class Assignment extends Expression {
	private final String name;
	private final Expression value;
	private final boolean local;

	public Assignment(String name, Expression value, boolean local) {
		this.name = name;
		this.value = value;
		this.local = local;
	}

	@Override
	public Value evaluate(Scope scope) throws RuntimeException {
		Value eval = value.evaluate(scope);
		
		if (local)
			scope.add(name, eval);
		else
			scope.getGlobalScope().add(name, eval);
		
		return eval;
	}
	
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		
		Assignment other = (Assignment)o;

		return local == other.local && name.equals(other.name) && value.equals(other.value);
	}


	@Override
	public int hashCode(){
		int result = super.hashCode();
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (value != null ? value.hashCode() : 0);
		result = 31 * result + (local ? 1 : 0);
		return result;
	}


	@Override
	public String toPrettyString(String indent){
		StringBuilder stringBuilder = new StringBuilder();
		if (local)
			stringBuilder.append("local ");

		return stringBuilder
			.append(name)
			.append(" = ")
			.append(value.toPrettyString(indent)).toString();
	}
}
