package mimuw.idlearn.language.base;

import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.problems.ProblemPackage;

import java.io.EOFException;
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
	public Value<Void> evaluate(Scope scope) throws RuntimeException {
		Scanner scanner = pkg.getTestInputScanner();
		for (var v : variables) {
			if (!scanner.hasNextInt()) {
				throw new RuntimeException("Too many variables provided for loading input.");
			}
			int value = scanner.nextInt();
			scope.add(v.getName(), new Value<>(value));
		}
		return new Value<>(null);
	}
}
