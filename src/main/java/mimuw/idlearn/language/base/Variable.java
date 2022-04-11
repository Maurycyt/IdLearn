package mimuw.idlearn.language.base;

import mimuw.idlearn.language.environment.Scope;

public class Variable<T> implements Expression<T> {
	private final String name;

	public Variable(String name) {
		this.name = name;
	}

	public Variable(String name, Scope originScope) {
		this.name = name;
		originScope.add(name, new Value<>(0));
	}

	public Variable(String name, Scope originScope, T initialValue) {
		this.name = name;
		originScope.add(name, new Value<>(initialValue));
	}

	public String getName() {
		return name;
	}

	@Override
	public Value<T> evaluate(Scope scope) throws RuntimeException {
		return scope.getVariable(name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;

		Variable<T> other = (Variable<T>) o;

		return name.equals(other.name);
	}

}
