package mimuw.idlearn.idlang.logic.base;

import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;

import java.io.Writer;
import java.util.Scanner;

public abstract class Expression {
	public double delay = 1;
	public Type type;
	abstract public Value evaluate(Scope scope, TimeCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException;
	public void assertType(Type expected) {
		if (expected != type) {
			throw new AssertionError("Required expression of type " + expected + "but got an argument of type " + type);
		}
	}
}
