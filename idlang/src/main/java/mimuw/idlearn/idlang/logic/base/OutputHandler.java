package mimuw.idlearn.idlang.logic.base;

import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;
import mimuw.idlearn.packages.ProblemPackage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class OutputHandler implements Expression<Void> {
	private final static String separator = " ";
	private Expression<?>[] values;

	public OutputHandler(Expression<?>... values) {
		this.values = values;
	}

	public void takeValues(Expression<?>... variables) {
		this.values = variables;
	}

	@Override
	public Value<Void> evaluate(Scope scope, TimeCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
		try {
			for (var v : values) {
				counter.addTime(delay);
				outputWriter.write(separator + v.evaluate(scope, counter, inputScanner, outputWriter).getValue());
			}
			outputWriter.flush();
		} catch (IOException e) {
			throw new RuntimeException(e); // TODO: clean-up
		}
		return new Value<>(null);
	}
}
