package mimuw.idlearn.idlang.base;

import mimuw.idlearn.idlang.environment.Scope;
import mimuw.idlearn.idlang.exceptions.EndOfInputException;
import mimuw.idlearn.idlang.exceptions.SimulationException;
import mimuw.idlearn.packages.ProblemPackage;

import java.util.Scanner;

public class InputHandler implements Expression<Void> {
	private final ProblemPackage pkg;
	private Variable<?>[] variables;

	public InputHandler(ProblemPackage pkg, Variable<?>... variables) {
		this.pkg = pkg;
		this.variables = variables;
	}

	public void takeVariables(Variable<?>... variables) {
		this.variables = variables;
	}

	@Override
	public Value<Void> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
		Scanner scanner = pkg.getTestInputScanner();
		for (var v : variables) {
			counter.addTime(delay);
			if (!scanner.hasNextInt()) {
				throw new EndOfInputException();
			}
			int value = scanner.nextInt();
			scope.add(v.getName(), new Value<>(value));
		}
		return new Value<>(null);
	}
}
