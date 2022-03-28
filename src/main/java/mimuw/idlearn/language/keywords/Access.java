package mimuw.idlearn.language.keywords;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.language.TypeCheck;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Access extends Expression {
	private final Expression expression;
	private final String what;
	private final Expression id;

	public Access(Expression expression, String what, Expression id) {
		this.expression = expression;
		this.what = what;
		this.id = id;
	}

	@Override
	public Value evaluate(Scope scope) throws RuntimeException{
		if (id != null && what != null)
			throw new RuntimeException("Cannot access expression, both index and field");
		if (id == null && what == null)
			throw new RuntimeException("Cannot access expression, index and field not specified");
		
		Object object = expression.evaluate(scope).getValue();
		if (object.getClass().isArray()){
			if (id == null)
				throw new RuntimeException("Cannot access " + what + ", expression is an array");
			
			Value tmp = id.evaluate(scope);
			TypeCheck.isType(tmp.getValue(), Number.class);
			int id = ((Number)tmp.getValue()).intValue();
			
			int len = Array.getLength(object);
			if (id < 0 || len <= id)
				throw new RuntimeException("Cannot access " + id + " in an expression, index out of bounds");
			return new Value(Array.get(object, id));
		}
		else if (object instanceof List){
			if (id == null)
				throw new RuntimeException("Cannot access " + what + ", expression is an array");
			
			Value tmp = id.evaluate(scope);
			TypeCheck.isType(tmp.getValue(), Number.class);
			int id = ((Number)tmp.getValue()).intValue();
			
			ArrayList<?> arr = (ArrayList<?>)object;
			if (id < 0 || arr.size() <= id)
				throw new RuntimeException("Cannot access " + id + " in an expresion, index out of bounds");
			return new Value(arr.get(id));
		}
		throw new RuntimeException("Cannot access index or field: unexpected error");
	}
	
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		
		Access other = (Access)o;

		return expression.equals(other.expression) && what.equals(other.what) && id.equals(other.id);
	}

	@Override
	public int hashCode(){
		int result = super.hashCode();
		result = 31 * result + (expression != null ? expression.hashCode() : 0);
		result = 31 * result + (what != null ? what.hashCode() : 0);
		result = 31 * result + (id != null ? id.hashCode() : 0);
		return result;
	}

	@Override
	public String toPrettyString(String indent){
		StringBuilder out = new StringBuilder()
			.append(expression.toPrettyString(indent));
		if (id != null)
			return out
				.append("[")
				.append(id.toPrettyString(indent))
				.append("]").toString();
		else
			return out
				.append(".")
				.append(what).toString();
	}
}
