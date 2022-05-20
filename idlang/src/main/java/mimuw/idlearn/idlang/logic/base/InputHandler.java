package mimuw.idlearn.idlang.logic.base;

import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.EndOfInputException;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;
import mimuw.idlearn.packages.ProblemPackage;

import java.io.Writer;
import java.util.Scanner;

public class InputHandler implements Expression<Void> {
	private Variable<?>[] variables;

	public InputHandler(Variable<?>... variables) {
		this.variables = variables;
	}

	public void takeVariables(Variable<?>... variables) {
		this.variables = variables;
	}

	@Override
	public Value<Void> evaluate(Scope scope, TimeCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
		for (var v : variables) {
			counter.addTime(delay);
			if (!inputScanner.hasNextInt()) {
				throw new EndOfInputException();
			}
			int value = inputScanner.nextInt();
			scope.add(v.getName(), new Value<>(value));
		}
		return new Value<>(null);
	}
}
