package mimuw.idlearn.language.base;

import mimuw.idlearn.language.environment.Scope;

public abstract class Expression {
	public final static String tabIndent = "    ";

	public abstract Value evaluate(Scope scope) throws RuntimeException;
	public abstract String toPrettyString(String indent);
}
