package mimuw.idlearn.idlangblocks.GUI.parser;

import mimuw.idlearn.idlang.logic.base.*;

public class StringToExpression {
	/**
	 * Parses a String into an expression (variable or constant)
	 *
	 * @param text The string
	 * @return Equivalent expression
	 */
	public static Expression parse(String text) {
		try {
			long number = Long.parseLong(text);
			return new Constant(new Value(Type.Long, number));
		} catch (NumberFormatException e) {
			return new Variable(Type.Long, text);
		}
	}
}
