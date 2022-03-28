package mimuw.idlearn.language.operators;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.language.TypeCheck;

public class Or extends TwoArgumentOperator{

	public Or(Expression arg1, Expression arg2) {
		super(arg1, arg2);
	}

	@Override
	public Value evaluate(Scope scope) throws RuntimeException{
		Object v1 = arg1.evaluate(scope).getValue();
		TypeCheck.assertType(v1, Boolean.class);
		Object v2 = arg2.evaluate(scope).getValue();
		TypeCheck.assertType(v2, Boolean.class);
		return new Value(((Boolean)v1) || ((Boolean)v2));
	}
	
	@Override
	public String toPrettyString(String indent){
		return arg1.toPrettyString(indent) + " | " + arg2.toPrettyString(indent);
	}
}
