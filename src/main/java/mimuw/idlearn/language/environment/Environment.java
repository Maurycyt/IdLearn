package mimuw.idlearn.language.environment;

import mimuw.idlearn.language.base.Function;
import mimuw.idlearn.language.base.Value;

import java.util.HashMap;
import java.util.Map;

public class Environment{
	protected final Map<String, Value> variables;
	protected final Map<String, Function> functions;

	public Environment() {
		this.variables = new HashMap<>();
		this.functions = new HashMap<>();
	}
	
	public Value getVariable(String name) throws RuntimeException{
		if (!variables.containsKey(name))
			throw new RuntimeException("variable " + name + " not found in the environment");
		return variables.get(name);
	}
	
	public Function getFunction(String name) throws RuntimeException{
		if (!functions.containsKey(name))
			throw new RuntimeException("function " + name + " not found in the environment");
		return functions.get(name);
	}
	
	public boolean containsVariable(String name) {
		return variables.containsKey(name);
	}
	
	public boolean containsFunction(String name) {
		return functions.containsKey(name);
	}
	
	public void add(String name, Value value) {
		variables.put(name, value);
	}
	
	public void add(String name, Function function) {
		functions.put(name, function);
	}
	
	public void clear() {
		variables.clear();
		functions.clear();
	}
	
	@Override
	public String toString() {
		return "Environment{" + "variables=" + variables + ", functions=" + functions + '}';
	}
}
