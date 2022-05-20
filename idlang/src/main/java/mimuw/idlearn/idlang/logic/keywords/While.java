package mimuw.idlearn.idlang.logic.keywords;

import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.TimeCounter;
import mimuw.idlearn.idlang.logic.base.Value;
import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;

import java.io.Writer;
import java.util.Scanner;

public class While implements Expression<Void> {
	private final Expression<Boolean> condition;
	private final Block body;

	public While(Expression<Boolean> condition, Block body) {
		this.condition = condition;
		this.body = body;
	}

	@Override
	public Value<Void> evaluate(Scope scope, TimeCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
		while (condition.evaluate(scope, counter, inputScanner, outputWriter).getValue()) {
			counter.addTime(delay);
			body.evaluate(new Scope(scope), counter, inputScanner, outputWriter);
		}
		return new Value<>(null);
	}
}
