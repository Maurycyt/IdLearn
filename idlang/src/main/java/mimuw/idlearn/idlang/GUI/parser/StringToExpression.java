package mimuw.idlearn.idlang.GUI.parser;

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
			int number = Integer.parseInt(text);
			return new Constant(new Value(Type.Integer, number));
		} catch (NumberFormatException e) {
			return new Variable(Type.Integer, text);
		}
	}
}
