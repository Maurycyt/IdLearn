package mimuw.idlearn.idlang.logic.base;

import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.EndOfInputException;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;

import java.io.Writer;
import java.util.Scanner;

import static mimuw.idlearn.idlang.logic.base.Type.Null;

public class InputHandler extends Expression {
	private Variable[] variables;

	public InputHandler(Variable... variables) {
		this.variables = variables;
	}


	@Override
	public Value evaluate(Scope scope, TimeCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {

		Object value;
		for (var variable : variables) {
			counter.addTime(delay);

			switch (variable.getType()) {
				case Integer:
					if (!inputScanner.hasNextInt()) {
						throw new EndOfInputException();
					}
					value = inputScanner.nextInt();
					break;
				default:
					throw new Error("Impossible data type - backend somehow allows parsing something imparsable");
			}

			scope.add(variable.getName(), new Value(Type.Integer, value));
		}

		return new Value(Null, null);
	}
}
