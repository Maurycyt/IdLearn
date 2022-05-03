package mimuw.idlearn.idlang.base;

import mimuw.idlearn.idlang.environment.Scope;
import mimuw.idlearn.idlang.exceptions.SimulationException;

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
	public Value<T> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
		return scope.getVariable(name);
	}
}
