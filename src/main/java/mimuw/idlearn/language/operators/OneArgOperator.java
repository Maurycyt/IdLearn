package mimuw.idlearn.language.operators;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;

public abstract class OneArgOperator<T> extends Operator<T> {
	protected Expression<T> arg;

	public OneArgOperator(Expression<T> arg) {
		this.arg = arg;
	}

	public static OneArgOperator<Boolean> newNot(Expression<Boolean> arg) {
		return new OneArgOperator<>(arg) {
			@Override
			public Value<Boolean> evaluate(Scope scope) throws RuntimeException {
				return new Value<>(!arg.evaluate(scope).getValue());
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
		
		OneArgOperator<T> other = (OneArgOperator<T>)o;
		
		return arg.equals(other.arg);
	}

	@Override
	public int hashCode(){
		int result = super.hashCode();
		result = 31 * result + (arg != null ? arg.hashCode() : 0);
		return result;
	}
}
