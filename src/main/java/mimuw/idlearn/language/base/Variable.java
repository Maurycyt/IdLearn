package mimuw.idlearn.language.base;

import mimuw.idlearn.language.environment.Scope;

import java.util.Objects;

public class Variable extends Expression {
	private String name;
	
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
		
		Variable variable = (Variable)o;
		
		return Objects.equals(name, variable.name);
	}
	
	@Override
	public String toPrettyString(String indent){
		return "var " + name;
	}
}
