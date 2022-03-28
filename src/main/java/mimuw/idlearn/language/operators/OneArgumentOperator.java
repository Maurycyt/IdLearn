package mimuw.idlearn.language.operators;

import mimuw.idlearn.language.base.Expression;

public abstract class OneArgumentOperator extends Expression {
	protected Expression arg;

	public OneArgumentOperator(Expression arg) {
		this.arg = arg;
	}

	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;
		if(!super.equals(o))
			return false;
		
		OneArgumentOperator other = (OneArgumentOperator)o;
		
		return arg.equals(other.arg);
	}

	@Override
	public int hashCode(){
		int result = super.hashCode();
		result = 31 * result + (arg != null ? arg.hashCode() : 0);
		return result;
	}
}
