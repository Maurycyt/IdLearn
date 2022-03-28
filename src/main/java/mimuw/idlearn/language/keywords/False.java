package mimuw.idlearn.language.keywords;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;

public class False extends Expression {

	public False() {}

	@Override
	public Value evaluate(Scope scope) throws RuntimeException{
		return new Value(Boolean.FALSE);
	}
	
	@Override
	public String toPrettyString(String indent){
		return "false";
	}
}
