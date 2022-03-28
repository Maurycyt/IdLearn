package mimuw.idlearn.language.operators;

import mimuw.idlearn.language.base.Expression;


public abstract class TwoArgumentOperator extends Expression {
	protected Expression arg1;
	protected Expression arg2;

	public TwoArgumentOperator(Expression arg1, Expression arg2) {
		this.arg1 = arg1;
		this.arg2 = arg2;
	}

	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;
		if(!super.equals(o))
			return false;
		
		TwoArgumentOperator other = (TwoArgumentOperator)o;
		
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
