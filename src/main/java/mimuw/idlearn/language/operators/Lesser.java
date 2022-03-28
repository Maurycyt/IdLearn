package mimuw.idlearn.language.operators;

import mimuw.idlearn.language.base.Expression;

public class Lesser extends RelationalOperator{

	public Lesser(Expression arg1, Expression arg2) {
		super(arg1, arg2);
	}

	@Override
	protected Boolean operation(Number arg1, Number arg2){
		if(arg1 instanceof Double || arg2 instanceof Double)
			return arg1.doubleValue() < arg2.doubleValue();
		else if(arg1 instanceof Float || arg2 instanceof Float)
			return arg1.floatValue() < arg2.floatValue();
		else if(arg1 instanceof Long || arg2 instanceof Long)
			return arg1.longValue() < arg2.longValue();
		else
			return arg1.intValue() < arg2.intValue();
	}
	
	@Override
	public String toPrettyString(String indent){
		return arg1.toPrettyString(indent) + " < " + arg2.toPrettyString(indent);
	}
}
