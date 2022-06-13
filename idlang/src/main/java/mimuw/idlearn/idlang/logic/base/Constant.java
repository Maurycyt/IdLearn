package mimuw.idlearn.idlang.logic.base;

import mimuw.idlearn.idlang.logic.environment.Scope;

import java.io.Writer;
import java.util.Scanner;

public class Constant extends Expression {
	private final Value value;

	public Constant(Value value) {
		this.value = value;
		this.type = value.type;
	}
	public Constant(Long value) {
		this.value = new Value(Type.Long, value);
		this.type = Type.Long;
	}
	public Constant(Integer value) {
		this.value = new Value(Type.Long, value.longValue());
		this.type = Type.Long;
	}

	@Override
	public Value evaluate(Scope scope, ResourceCounter counter, Scanner inputScanner, Writer outputWriter) {
		return value;
	}
}
