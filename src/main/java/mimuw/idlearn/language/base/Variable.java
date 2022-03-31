package mimuw.idlearn.language.base;

import mimuw.idlearn.language.environment.Scope;

public class Variable<T> implements Expression<T> {
	private final String name;

	public Variable(String name) {
		this.name = name;
	}

	@Override
	public Value<T> evaluate(Scope scope) throws RuntimeException{
		return (Value<T>)scope.getVariable(name);
	}
	
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		
		Variable<T> other = (Variable<T>)o;
		
		return name.equals(other.name);
	}

}
