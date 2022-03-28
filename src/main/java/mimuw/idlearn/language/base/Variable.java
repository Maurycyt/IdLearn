package mimuw.idlearn.language.base;

import mimuw.idlearn.language.environment.Scope;

public class Variable extends Expression {
	private final String name;

	public Variable(String name) {
		this.name = name;
	}

	@Override
	public Value evaluate(Scope scope) throws RuntimeException{
		return scope.getVariable(name);
	}
	
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		
		Variable other = (Variable)o;
		
		return name.equals(other.name);
	}
	
	@Override
	public String toPrettyString(String indent){
		return "var " + name;
	}
}
