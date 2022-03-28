package mimuw.idlearn.language.base;

import mimuw.idlearn.language.environment.Scope;

import java.util.Arrays;
import java.util.Objects;

public class FunctionCall extends Expression {
	private String name;
	private Expression[] arguments;
	
	@Override
	public Value evaluate(Scope scope) throws RuntimeException{
		return scope.getFunction(name).call(scope, arguments);
	}
	
	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;
		if(!super.equals(o))
			return false;
		
		FunctionCall other = (FunctionCall)o;
		
		if(!Objects.equals(name, other.name))
			return false;
		// Probably incorrect - comparing Object[] arrays with Arrays.equals
		return Arrays.equals(arguments, other.arguments);
	}

	@Override
	public String toPrettyString(String indent){
		StringBuilder out = new StringBuilder()
			.append(name)
			.append("(");

		for (Expression arg : arguments){
			out.append(arg.toPrettyString(indent));
			if (!arg.equals(arguments[arguments.length - 1]))
				out.append(", ");
		}

		return out
				.append(")")
				.toString();
	}
}
