package mimuw.idlearn.idlang.logic.base;

import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.BadTypeException;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;

import java.io.Writer;
import java.util.Scanner;

public class Variable extends Expression {
	private final String name;


	public Variable(Type type, String name) {
		this.name = name;
		this.type = type;
	}

	public Variable(Type type, String name, Scope originScope) {
		this.name = name;
		this.type = type;
		originScope.add(name, new Value(type, 0));
	}

	public Variable(String name, Scope originScope, Value initialValue) {
		this.name = name;
		this.type = initialValue.type;
		originScope.add(name, new Value(initialValue.type, initialValue.value));
	}
	public Variable(String name, Scope originScope, long initialValue) {
		this.name = name;
		this.type = Type.Long;
		originScope.add(name, new Value(this.type, initialValue));
	}

	public String getName() {
		return name;
	}
	public Type getType() {return type;}

	@Override
	public Value evaluate(Scope scope, ResourceCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
		Value value = scope.getVariable(name);
		if (value.type != this.type) {
			throw new BadTypeException(this.type, value.type, this.name);
		}
		return value;
	}
}
