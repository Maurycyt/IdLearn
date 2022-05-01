package mimuw.idlearn.language.base;

import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.language.exceptions.SimulationException;

public class Value<T> implements Expression<T> {
	private final T value;

	public Value(T value) {
		this.value = value;
	}

	@Override
	public Value<T> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
		return this;
	}

	public T getValue() {
		return value;
	}
}
