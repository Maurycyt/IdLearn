package mimuw.idlearn.language.base;

import mimuw.idlearn.language.environment.Scope;

public class Value extends Expression {
	private final Object value;

	public Value(Object value){
		this.value = value;
	}
	
	@Override
	public Value evaluate(Scope scope) throws RuntimeException{
		return this;
	}
	
	public Object getValue(){
		return value;
	}

	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;
		if(!super.equals(o))
			return false;
		
		Value other = (Value)o;
		
		return value.equals(other.value);
	}
	
	@Override
	public int hashCode(){
		int result = super.hashCode();
		result = 31 * result + (value != null ? value.hashCode() : 0);
		return result;
	}
	
	@Override
	public String toPrettyString(String indent){
		return value.toString();
	}
}
