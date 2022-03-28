package mimuw.idlearn.language.base;

import mimuw.idlearn.language.environment.Scope;

public abstract class Expression {
	public final static String tabIndent = "    ";
	private String type;

	public abstract Value evaluate(Scope scope) throws RuntimeException;
	
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		
		Expression expression = (Expression)o;
		
		return type.equals(expression.type);
	}

	public abstract String toPrettyString(String indent);
}
