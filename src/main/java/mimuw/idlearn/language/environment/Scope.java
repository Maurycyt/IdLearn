package mimuw.idlearn.language.environment;

import mimuw.idlearn.language.base.Value;

import java.util.HashMap;
import java.util.Map;

public class Scope {
	private final Map<String, Value<?>> variables;
	private final Scope parentScope;
	private final Scope globalScope;

	public Scope(Scope parentScope){
		this.variables = new HashMap<>();
		this.parentScope = parentScope;
		this.globalScope = parentScope.getGlobalScope();
	}

	public Scope(){
		this.variables = new HashMap<>();
		this.parentScope = null;
		this.globalScope = this;
	}

	public Scope getGlobalScope(){
		return globalScope;
	}

	public boolean isGlobal(){
		return globalScope == this && parentScope == null;
	}

	public Scope getParentScope(){
		return parentScope;
	}

	public boolean containsVariable(String name) {
		return variables.containsKey(name);
	}

	public <T> Value<T> getVariable(String name) throws RuntimeException{
		if (!variables.containsKey(name)) {
			if (isGlobal())
				throw new RuntimeException("Variable not found in scope"); //TODO: could be replaced by initializing the variable to 0
			return parentScope.getVariable(name);
		}
		return (Value<T>) variables.get(name);
	}

	public Scope getOriginScope(String name) {
		if (isGlobal())
			return variables.containsKey(name)? this : null;

		Scope origin = parentScope.getOriginScope(name);
		if (origin != null)
			return origin;

		return variables.containsKey(name)? this : null;
	}

	public void add(String name, Value<?> value) {
		variables.put(name, value);
	}

	public void clear() {
		variables.clear();
	}
}
