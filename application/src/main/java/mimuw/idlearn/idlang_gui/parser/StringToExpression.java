package mimuw.idlearn.idlang_gui.parser;

import mimuw.idlearn.idlang.base.Expression;
import mimuw.idlearn.idlang.base.Value;
import mimuw.idlearn.idlang.base.Variable;

public class StringToExpression {
	/**
	 * Parses a String into an expression (variable or constant)
	 *
	 * @param text The string
	 * @return Equivalent expression
	 */
	public static Expression<Integer> parse(String text) {
		try {
			int number = Integer.parseInt(text);
			return new Value<>(number);
		} catch (NumberFormatException e) {
			return new Variable<>(text);
		}
	}
}
