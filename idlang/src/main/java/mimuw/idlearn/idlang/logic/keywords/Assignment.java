package mimuw.idlearn.idlang.logic.keywords;

import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.ResourceCounter;
import mimuw.idlearn.idlang.logic.base.Value;
import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;

import java.io.Writer;
import java.util.Scanner;

import static mimuw.idlearn.idlang.logic.base.Type.Null;

public class Assignment extends Expression {
	private final String name;
	private final Expression expression;
	private final boolean shouldTakeTime;

	public Assignment(String name, Expression expression, boolean shouldTakeTime) {
		this.name = name;
		this.expression = expression;
		this.type = Null;
		this.shouldTakeTime = shouldTakeTime;
	}

	@Override
	public Value evaluate(Scope scope, ResourceCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
		Value eval = expression.evaluate(scope, counter, inputScanner, outputWriter);

		Scope origin = scope.getOriginScope(name);

		if (shouldTakeTime) {
			counter.addTime(delay);
		}

		if (origin == null)
			scope.add(name, eval);
		else
			origin.add(name, eval);

		return new Value(Null, null);
	}
}
