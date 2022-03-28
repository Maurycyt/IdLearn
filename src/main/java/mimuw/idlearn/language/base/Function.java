package mimuw.idlearn.language.base;

import mimuw.idlearn.language.keywords.Block;
import mimuw.idlearn.language.environment.Scope;

import java.util.Arrays;
import java.util.Objects;

public class Function extends Expression {
	private String name;
	private String[] argsNames;
	private Expression body;
	private boolean local = false;
	
	transient private Scope baseScope;
	
	@Override
	public Value evaluate(Scope scope) throws RuntimeException{
		
		if(local){
			scope.add(name, this);
			baseScope = new Scope(scope);
		}
		else{
			scope.getGlobalScope().add(name, this);
			baseScope = new Scope(scope.getGlobalScope());
		}
		
		return new Value(0);
	}
	
	public Value call(Scope scope, Expression... args) throws RuntimeException{
		if(argsNames.length != args.length)
			throw new RuntimeException("Invalid function call: " + name + " requires " + argsNames.length + " arguments, " + args.length + " provided");
		
		Scope callScope = new Scope(baseScope);
		
		for(int i = 0; i < args.length; i++)
			callScope.add(argsNames[i], args[i].evaluate(scope));
		
		return body.evaluate(callScope);
	}
	
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		
		Function function = (Function)o;
		
		if(local != function.local)
			return false;
		if(!Objects.equals(name, function.name))
			return false;
		// Probably incorrect - comparing Object[] arrays with Arrays.equals
		if(!Arrays.equals(argsNames, function.argsNames))
			return false;
		return Objects.equals(body, function.body);
	}
	
	@Override
	public int hashCode(){
		int result = super.hashCode();
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + Arrays.hashCode(argsNames);
		result = 31 * result + (body != null ? body.hashCode() : 0);
		result = 31 * result + (local ? 1 : 0);
		return result;
	}
	
	@Override
	public String toPrettyString(String indent){
		StringBuilder out = new StringBuilder();
		if(local)
			out.append("local ");
		out.append("def " + name + "(");
		for(String arg : argsNames){
			out.append(arg);
			if(!arg.equals(argsNames[argsNames.length - 1]))
				out.append(", ");
		}
		out.append(")");
		if(body.getClass() != Block.class)
			out.append("\n" + indent + "    " + body.toPrettyString(indent + "    "));
		else
			out.append(body.toPrettyString(indent));
		return out.toString();
	}
}
