package mimuw.idlearn.language.keywords;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;

public class True extends Expression {

	public True() {}

	@Override
	public Value evaluate(Scope scope) throws RuntimeException{
		return new Value(Boolean.TRUE);
	}
	
	@Override
	public String toPrettyString(String indent){
		return "true";
	}
}
