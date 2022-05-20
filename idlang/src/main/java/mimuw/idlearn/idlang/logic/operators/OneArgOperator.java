package mimuw.idlearn.idlang.logic.operators;

import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.TimeCounter;
import mimuw.idlearn.idlang.logic.base.Value;
import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;

import java.io.Writer;
import java.util.Scanner;

public abstract class OneArgOperator<A, R> implements Expression<R> {
	protected Expression<A> arg;

	private OneArgOperator(Expression<A> arg) {
		this.arg = arg;
	}

	public static OneArgOperator<Boolean, Boolean> newNot(Expression<Boolean> arg) {
		return new OneArgOperator<>(arg) {
			@Override
			public Value<Boolean> evaluate(Scope scope, TimeCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
				counter.addTime(delay);
				return new Value<>(!arg.evaluate(scope, counter, inputScanner, outputWriter).getValue());
			}
		};
	}
}
