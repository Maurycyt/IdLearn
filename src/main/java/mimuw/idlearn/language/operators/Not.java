package mimuw.idlearn.language.operators;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;

public class Not extends OneArgumentOperator{

	public Not(Expression arg) {
		super(arg);
	}

	@Override
	public Value evaluate(Scope scope) throws RuntimeException{
		Object v = arg.evaluate(scope).getValue();
		return new Value(!((Boolean)v));
	}
	
	@Override
	public String toPrettyString(String indent){
		return "!" + arg.toPrettyString(indent);
	}
}
