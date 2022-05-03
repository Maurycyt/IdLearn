package mimuw.idlearn.idlang.logic.base;

import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;

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
