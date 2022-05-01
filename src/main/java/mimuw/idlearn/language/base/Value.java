package mimuw.idlearn.language.base;

import mimuw.idlearn.language.environment.Scope;

public class Value<T> implements Expression<T> {
	private final T value;

	public Value(T value) {
		this.value = value;
	}

	@Override
	public Value<T> evaluate(Scope scope) throws RuntimeException {
		return this;
	}

	public T getValue() {
		return value;
	}
}
