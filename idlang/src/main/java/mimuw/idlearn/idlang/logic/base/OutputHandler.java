package mimuw.idlearn.idlang.logic.base;

import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;

import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

import static mimuw.idlearn.idlang.logic.base.Type.Null;

public class OutputHandler extends Expression {
	private final static String separator = " ";
	private final Expression[] values;

	public OutputHandler(Expression... values) {
		this.values = values;
	}


	@Override
	public Value evaluate(Scope scope, ResourceCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {

		for (var value : values) {

			try {
				counter.addTime(delay);
				outputWriter.write(separator + value.evaluate(scope, counter, inputScanner, outputWriter));

				outputWriter.flush();
			} catch (IOException e) {
				throw new RuntimeException(e); // TODO: clean-up
			}
		}
		return new Value(Null, null);
	}
}
