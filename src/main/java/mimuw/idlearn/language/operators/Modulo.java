package mimuw.idlearn.language.operators;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;

public class Modulo extends TwoArgumentOperator{

	public Modulo(Expression arg1, Expression arg2) {
		super(arg1, arg2);
	}

	@Override
	public Value evaluate(Scope scope) throws RuntimeException{
		Object v1 = arg1.evaluate(scope).getValue();
		Object v2 = arg2.evaluate(scope).getValue();
		Number n1 = (Number)v1;
		Number n2 = (Number)v2;

		return new Value(n1.longValue() % n2.longValue());
	}
	
	@Override
	public String toPrettyString(String indent){
		return arg1.toPrettyString(indent) + " % " + arg2.toPrettyString(indent);
	}
}
