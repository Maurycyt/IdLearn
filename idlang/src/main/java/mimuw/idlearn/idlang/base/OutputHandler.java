package mimuw.idlearn.idlang.base;

import mimuw.idlearn.idlang.environment.Scope;
import mimuw.idlearn.idlang.exceptions.SimulationException;
import mimuw.idlearn.problem_package_system.ProblemPackage;

import java.io.FileWriter;
import java.io.IOException;

public class OutputHandler implements Expression<Void> {
	private final static String separator = " ";
	private final ProblemPackage pkg;
	private Expression<?>[] values;

	public OutputHandler(ProblemPackage pkg, Expression<?>... values) {
		this.pkg = pkg;
		this.values = values;
	}

	public void takeValues(Expression<?>... variables) {
		this.values = variables;
	}

	@Override
	public Value<Void> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
		FileWriter writer = pkg.getTestOutputWriter();
		try {
			for (var v : values) {
				counter.addTime(delay);
				writer.write(separator + v.evaluate(scope, counter).getValue());
			}
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e); // TODO: clean-up
		}
		return new Value<>(null);
	}
}
