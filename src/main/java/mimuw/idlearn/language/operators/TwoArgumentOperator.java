package mimuw.idlearn.language.operators;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;

public abstract class TwoArgumentOperator<T> extends Operator<T> {
	protected Expression<T> arg1;
	protected Expression<T> arg2;

	public TwoArgumentOperator(Expression<T> arg1, Expression<T> arg2) {
		this.arg1 = arg1;
		this.arg2 = arg2;
	}

	public static TwoArgumentOperator<Integer> newAdd(Expression<Integer> arg1, Expression<Integer> arg2) {
		return new TwoArgumentOperator<>(arg1, arg2) {
			@Override
			public Value<Integer> evaluate(Scope scope) throws RuntimeException {
				return new Value<>(arg1.evaluate(scope).getValue() + arg2.evaluate(scope).getValue());
			}
		};
	}

	public static TwoArgumentOperator<Integer> newSub(Expression<Integer> arg1, Expression<Integer> arg2) {
		return new TwoArgumentOperator<>(arg1, arg2) {
			@Override
			public Value<Integer> evaluate(Scope scope) throws RuntimeException {
				return new Value<>(arg1.evaluate(scope).getValue() - arg2.evaluate(scope).getValue());
			}
		};
	}

	public static TwoArgumentOperator<Integer> newMul(Expression<Integer> arg1, Expression<Integer> arg2) {
		return new TwoArgumentOperator<>(arg1, arg2) {
			@Override
			public Value<Integer> evaluate(Scope scope) throws RuntimeException {
				return new Value<>(arg1.evaluate(scope).getValue() * arg2.evaluate(scope).getValue());
			}
		};
	}

	public static TwoArgumentOperator<Integer> newDiv(Expression<Integer> arg1, Expression<Integer> arg2) {
		return new TwoArgumentOperator<>(arg1, arg2) {
			@Override
			public Value<Integer> evaluate(Scope scope) throws RuntimeException {
				return new Value<>(arg1.evaluate(scope).getValue() / arg2.evaluate(scope).getValue());
			}
		};
	}

	public static TwoArgumentOperator<Boolean> newOr(Expression<Boolean> arg1, Expression<Boolean> arg2) {
		return new TwoArgumentOperator<>(arg1, arg2) {
			@Override
			public Value<Boolean> evaluate(Scope scope) throws RuntimeException {
				return new Value<>(arg1.evaluate(scope).getValue() || arg2.evaluate(scope).getValue());
			}
		};
	}

	public static TwoArgumentOperator<Boolean> newAnd(Expression<Boolean> arg1, Expression<Boolean> arg2) {
		return new TwoArgumentOperator<>(arg1, arg2) {
			@Override
			public Value<Boolean> evaluate(Scope scope) throws RuntimeException {
				return new Value<>(arg1.evaluate(scope).getValue() && arg2.evaluate(scope).getValue());
			}
		};
	}

	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;
		if(!super.equals(o))
			return false;
		
		TwoArgumentOperator<T> other = (TwoArgumentOperator<T>)o;
		
		return arg1.equals(other.arg1) && arg2.equals(other.arg2);
	}

	@Override
	public int hashCode(){
		int result = super.hashCode();
		result = 31 * result + (arg1 != null ? arg1.hashCode() : 0);
		result = 31 * result + (arg2 != null ? arg2.hashCode() : 0);
		return result;
	}
}
