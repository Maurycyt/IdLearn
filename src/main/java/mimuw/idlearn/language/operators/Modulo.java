package mimuw.idlearn.language.operators;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.language.TypeCheck;

import java.util.Arrays;

public class Modulo extends TwoArgumentOperator{

	public Modulo(Expression arg1, Expression arg2) {
		super(arg1, arg2);
	}

	@Override
	public Value evaluate(Scope scope) throws RuntimeException{
		Object v1 = arg1.evaluate(scope).getValue();
		TypeCheck.assertType(v1, Number.class);
		Object v2 = arg2.evaluate(scope).getValue();
		TypeCheck.assertType(v2, Number.class);
		
		Number n1 = (Number)v1;
		if (n1.doubleValue() != n1.longValue())
			throw new RuntimeException("Object has type " + v1.getClass().getName() + ", " + Arrays.toString(new Class[]{Byte.class, Short.class, Integer.class, Long.class}) + " required");
		Number n2 = (Number)v2;
		if(n2.doubleValue() != n2.longValue())
			throw new RuntimeException("Object has type " + v2.getClass().getName() + ", " + Arrays.toString(new Class[]{Byte.class, Short.class, Integer.class, Long.class}) + " required");
		
		return new Value(n1.longValue() % n2.longValue());
	}
	
	@Override
	public String toPrettyString(String indent){
		return arg1.toPrettyString(indent) + " % " + arg2.toPrettyString(indent);
	}
}
