package mimuw.idlearn.language.keywords;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;

public class Assignment<T> implements Expression<T> {
	private final String name;
	private final Expression<T> value;
	private final boolean local; //todo: delete this

	public Assignment(String name, Expression<T> value, boolean local) {
		this.name = name;
		this.value = value;
		this.local = local;
	}

	public Assignment(String name, Expression<T> value) {
		this.name = name;
		this.value = value;
		this.local = true;
	}

	public Assignment(String name, T value) {
		this.name = name;
		this.value = new Value<>(value);
		this.local = false;
	}

	@Override
	public Value<T> evaluate(Scope scope) throws RuntimeException {
		Value<T> eval = value.evaluate(scope);

		Scope origin = scope.getOriginScope(name);

		if (origin == null)
			scope.add(name, eval);
		else
			origin.add(name, eval);

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
		
		Assignment<T> other = (Assignment<T>)o;

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


}
