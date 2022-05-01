package mimuw.idlearn.language.operators;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.TimeCounter;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.language.exceptions.SimulationException;

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
