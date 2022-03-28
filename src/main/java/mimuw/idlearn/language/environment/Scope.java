package mimuw.idlearn.language.environment;

import mimuw.idlearn.language.base.Function;
import mimuw.idlearn.language.base.Value;

public class Scope extends Environment{
	private final Scope parent;
	private final GlobalScope globalScope;

	public Scope(Scope parent){
		this.parent = parent;
		this.globalScope = parent.getGlobalScope();
	}

	public Scope(GlobalScope globalScope){
		this.parent = null;
		this.globalScope = globalScope;
	}

	@Override
	public Value getVariable(String name) throws RuntimeException{
		if (containsVariable(name))
			return super.getVariable(name);
		else if (parent != null)
			return parent.getVariable(name);
		return globalScope.getVariable(name);
	}
	
	@Override
	public Function getFunction(String name) throws RuntimeException{
		if (containsFunction(name))
			return super.getFunction(name);
		else if (parent != null)
			return parent.getFunction(name);
		return globalScope.getFunction(name);
	}
	
	public GlobalScope getGlobalScope(){
		return globalScope;
	}
	
	@Override
	public String toString(){
		return "Scope{" + "variables=" + variables + ", functions=" + functions + ", parent=" + (parent == null ? globalScope : parent) + '}';
	}
}
