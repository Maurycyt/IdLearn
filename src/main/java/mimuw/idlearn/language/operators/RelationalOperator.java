package mimuw.idlearn.language.operators;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;

public abstract class RelationalOperator extends TwoArgumentOperator{

	public RelationalOperator(Expression arg1, Expression arg2) {
		super(arg1, arg2);
	}

	protected abstract Boolean operation(Number arg1, Number arg2);
	
	@Override
	public Value evaluate(Scope scope) throws RuntimeException{
		Value v1 = arg1.evaluate(scope);
		Value v2 = arg2.evaluate(scope);
		return new Value(operation((Number)v1.getValue(), (Number)v2.getValue()));
	}
}
