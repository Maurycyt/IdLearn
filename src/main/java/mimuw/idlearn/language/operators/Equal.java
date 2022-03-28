package mimuw.idlearn.language.operators;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;

public class Equal extends TwoArgumentOperator{

	public Equal(Expression arg1, Expression arg2) {
		super(arg1, arg2);
	}

	@Override
	public Value evaluate(Scope scope) throws RuntimeException{
		return new Value(arg1.evaluate(scope).equals(arg2.evaluate(scope)));
	}
	
	@Override
	public String toPrettyString(String indent){
		return arg1.toPrettyString(indent) + " == " + arg2.toPrettyString(indent);
	}
}
