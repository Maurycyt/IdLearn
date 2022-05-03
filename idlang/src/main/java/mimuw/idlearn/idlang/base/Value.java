package mimuw.idlearn.idlang.base;

import mimuw.idlearn.idlang.environment.Scope;
import mimuw.idlearn.idlang.exceptions.SimulationException;

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
