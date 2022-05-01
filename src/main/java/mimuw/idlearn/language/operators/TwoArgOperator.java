package mimuw.idlearn.language.operators;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.TimeCounter;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.language.exceptions.SimulationException;

public abstract class TwoArgOperator<A, R> implements Expression<R> {
	protected Expression<A> arg1;
	protected Expression<A> arg2;

	private TwoArgOperator(Expression<A> arg1, Expression<A> arg2) {
		this.arg1 = arg1;
		this.arg2 = arg2;
	}

	public static TwoArgOperator<Integer, Integer> newAdd(Expression<Integer> arg1, Expression<Integer> arg2) {
		return new TwoArgOperator<>(arg1, arg2) {
			@Override
			public Value<Integer> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
				counter.addTime(delay);
				return new Value<>(arg1.evaluate(scope, counter).getValue() + arg2.evaluate(scope, counter).getValue());
			}
		};
	}

	public static TwoArgOperator<Integer, Integer> newSubtract(Expression<Integer> arg1, Expression<Integer> arg2) {
		return new TwoArgOperator<>(arg1, arg2) {
			@Override
			public Value<Integer> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
				counter.addTime(delay);
				return new Value<>(arg1.evaluate(scope, counter).getValue() - arg2.evaluate(scope, counter).getValue());
			}
		};
	}

	public static TwoArgOperator<Integer, Integer> newMultiply(Expression<Integer> arg1, Expression<Integer> arg2) {
		return new TwoArgOperator<>(arg1, arg2) {
			@Override
			public Value<Integer> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
				counter.addTime(delay);
				return new Value<>(arg1.evaluate(scope, counter).getValue() * arg2.evaluate(scope, counter).getValue());
			}
		};
	}

	public static TwoArgOperator<Integer, Integer> newDivide(Expression<Integer> arg1, Expression<Integer> arg2) {
		return new TwoArgOperator<>(arg1, arg2) {
			@Override
			public Value<Integer> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
				counter.addTime(delay);
				return new Value<>(arg1.evaluate(scope, counter).getValue() / arg2.evaluate(scope, counter).getValue());
			}
		};
	}


	public static TwoArgOperator<Integer, Integer> newModulo(Expression<Integer> arg1, Expression<Integer> arg2) {
		return new TwoArgOperator<>(arg1, arg2) {
			@Override
			public Value<Integer> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
				counter.addTime(delay);
				return new Value<>(arg1.evaluate(scope, counter).getValue() % arg2.evaluate(scope, counter).getValue());
			}
		};
	}

	public static TwoArgOperator<Boolean, Boolean> newOr(Expression<Boolean> arg1, Expression<Boolean> arg2) {
		return new TwoArgOperator<>(arg1, arg2) {
			@Override
			public Value<Boolean> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
				counter.addTime(delay);
				return new Value<>(arg1.evaluate(scope, counter).getValue() || arg2.evaluate(scope, counter).getValue());
			}
		};
	}

	public static TwoArgOperator<Boolean, Boolean> newAnd(Expression<Boolean> arg1, Expression<Boolean> arg2) {
		return new TwoArgOperator<>(arg1, arg2) {
			@Override
			public Value<Boolean> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
				counter.addTime(delay);
				return new Value<>(arg1.evaluate(scope, counter).getValue() && arg2.evaluate(scope, counter).getValue());
			}
		};
	}

	public static TwoArgOperator<Integer, Boolean> newLess(Expression<Integer> arg1, Expression<Integer> arg2) {
		return new TwoArgOperator<>(arg1, arg2) {
			@Override
			public Value<Boolean> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
				counter.addTime(delay);
				return new Value<>(arg1.evaluate(scope, counter).getValue() < arg2.evaluate(scope, counter).getValue());
			}
		};
	}

	public static TwoArgOperator<Integer, Boolean> newLessEqual(Expression<Integer> arg1, Expression<Integer> arg2) {
		return new TwoArgOperator<>(arg1, arg2) {
			@Override
			public Value<Boolean> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
				counter.addTime(delay);
				return new Value<>(arg1.evaluate(scope, counter).getValue() <= arg2.evaluate(scope, counter).getValue());
			}
		};
	}

	public static TwoArgOperator<Integer, Boolean> newGreater(Expression<Integer> arg1, Expression<Integer> arg2) {
		return new TwoArgOperator<>(arg1, arg2) {
			@Override
			public Value<Boolean> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
				counter.addTime(delay);
				return new Value<>(arg1.evaluate(scope, counter).getValue() > arg2.evaluate(scope, counter).getValue());
			}
		};
	}

	public static TwoArgOperator<Integer, Boolean> newGreaterEqual(Expression<Integer> arg1, Expression<Integer> arg2) {
		return new TwoArgOperator<>(arg1, arg2) {
			@Override
			public Value<Boolean> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
				counter.addTime(delay);
				return new Value<>(arg1.evaluate(scope, counter).getValue() >= arg2.evaluate(scope, counter).getValue());
			}
		};
	}

	public static TwoArgOperator<Integer, Boolean> newEqual(Expression<Integer> arg1, Expression<Integer> arg2) {
		return new TwoArgOperator<>(arg1, arg2) {
			@Override
			public Value<Boolean> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
				counter.addTime(delay);
				return new Value<>(arg1.evaluate(scope, counter).getValue().equals(arg2.evaluate(scope, counter).getValue()));
			}
		};
	}

	public static TwoArgOperator<Integer, Boolean> newNotEqual(Expression<Integer> arg1, Expression<Integer> arg2) {
		return new TwoArgOperator<>(arg1, arg2) {
			@Override
			public Value<Boolean> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
				counter.addTime(delay);
				return new Value<>(!arg1.evaluate(scope, counter).getValue().equals(arg2.evaluate(scope, counter).getValue()));
			}
		};
	}
}
