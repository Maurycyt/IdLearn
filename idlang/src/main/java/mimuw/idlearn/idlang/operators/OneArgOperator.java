package mimuw.idlearn.idlang.operators;

import mimuw.idlearn.idlang.base.Expression;
import mimuw.idlearn.idlang.base.TimeCounter;
import mimuw.idlearn.idlang.base.Value;
import mimuw.idlearn.idlang.environment.Scope;
import mimuw.idlearn.idlang.exceptions.SimulationException;

public abstract class OneArgOperator<A, R> implements Expression<R> {
	protected Expression<A> arg;

	private OneArgOperator(Expression<A> arg) {
		this.arg = arg;
	}

	public static OneArgOperator<Boolean, Boolean> newNot(Expression<Boolean> arg) {
		return new OneArgOperator<>(arg) {
			@Override
			public Value<Boolean> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
				counter.addTime(delay);
				return new Value<>(!arg.evaluate(scope, counter).getValue());
			}
		};
	}
}
