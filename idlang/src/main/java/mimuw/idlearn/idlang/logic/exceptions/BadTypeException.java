package mimuw.idlearn.idlang.logic.exceptions;

import mimuw.idlearn.idlang.logic.base.Type;

public class BadTypeException extends SimulationException {
	public BadTypeException(Type expected, Type actual, String name) {
		super("Expected type " + expected + " but variable " + name + " is of type " + actual);
	}
}
