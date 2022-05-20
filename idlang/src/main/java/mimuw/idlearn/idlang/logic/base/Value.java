package mimuw.idlearn.idlang.logic.base;

import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.BadTypeException;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;

import java.io.Writer;
import java.util.Scanner;


public class Value {
	public Object value;
	public Type type;

	public Value(Type type, Object value) {
		this.type = type;
		this.value = value;
	}

	@Override
	public String toString() {
		switch (type) {
			case Integer -> {
				return ((Integer)value).toString();
			}
			default -> throw new Error("Impossible data type - backend somehow allows writing something imparsable");

		}
	}
}
