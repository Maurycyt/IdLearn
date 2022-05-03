package mimuw.idlearn.idlang.logic.environment;

import mimuw.idlearn.idlang.logic.base.Value;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;
import mimuw.idlearn.idlang.logic.exceptions.UndefinedException;

import java.util.HashMap;
import java.util.Map;

public class Scope {
	private final Map<String, Value<?>> variables;
	private final Scope parentScope;

	public Scope(Scope parentScope) {
		this.variables = new HashMap<>();
		this.parentScope = parentScope;
	}

	public Scope() {
		this.variables = new HashMap<>();
		this.parentScope = null;
	}

	public boolean isGlobal() {
		return parentScope == null;
	}

	public Scope getParentScope() {
		return parentScope;
	}

	public boolean containsVariable(String name) {
		return variables.containsKey(name);
	}

	public <T> Value<T> getVariable(String name) throws SimulationException {
		Scope scope = getOriginScope(name);
		if (scope == null)
			throw new UndefinedException(name);
		return (Value<T>) scope.variables.get(name);
	}

	public Scope getOriginScope(String name) {
		if (isGlobal())
			return variables.containsKey(name) ? this : null; // TODO: comment on refactor

		Scope origin = parentScope.getOriginScope(name);
		if (origin != null)
			return origin;

		return variables.containsKey(name) ? this : null;
	}

	public void add(String name, Value<?> value) {
		variables.put(name, value);
	}

	public void clear() {
		variables.clear();
	}
}
