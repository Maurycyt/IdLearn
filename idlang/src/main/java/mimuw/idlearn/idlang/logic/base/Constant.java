package mimuw.idlearn.idlang.logic.base;

import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;

import java.io.Writer;
import java.util.Scanner;

public class Constant extends Expression {
	private final Value value;

	public Constant(Value value) {
		this.value = value;
		this.type = value.type;
	}
	public Constant(Integer value) {
		this.value = new Value(Type.Integer, value);
		this.type = Type.Integer;
	}

	@Override
	public Value evaluate(Scope scope, TimeCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
		return value;
	}
}
