package mimuw.idlearn.idlang.logic.operators;

import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.ResourceCounter;
import mimuw.idlearn.idlang.logic.base.Type;
import mimuw.idlearn.idlang.logic.base.Value;
import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;

import java.io.Writer;
import java.util.Scanner;

public abstract class TwoArgOperator extends Expression {
	protected final Expression arg1;
	protected final Expression arg2;
	private static Value boolToLongVal(boolean bool) {
		if (bool) {
			return new Value(Type.Long, 1L);
		}
		else {
			return new Value(Type.Long, 0L);
		}
	}

	private TwoArgOperator(Expression arg1, Expression arg2, Type resultType) {
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.type = resultType;
	}

	public static TwoArgOperator newAdd(Expression arg1, Expression arg2) {
		arg1.assertType(Type.Long);
		arg2.assertType(Type.Long);
		return new TwoArgOperator(arg1, arg2, Type.Long) {
			@Override
			public Value evaluate(Scope scope, ResourceCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
				counter.addTime(delay);
				return new Value(this.type, (Long)arg1.evaluate(scope, counter, inputScanner, outputWriter).value + (Long)arg2.evaluate(scope, counter, inputScanner, outputWriter).value);
			}
		};
	}

	public static TwoArgOperator newSubtract(Expression arg1, Expression arg2) {
		return new TwoArgOperator(arg1, arg2, Type.Long) {
			@Override
			public Value evaluate(Scope scope, ResourceCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
				counter.addTime(delay);
				return new Value(this.type, (Long)arg1.evaluate(scope, counter, inputScanner, outputWriter).value - (Long)arg2.evaluate(scope, counter, inputScanner, outputWriter).value);
			}
		};
	}

	public static TwoArgOperator newMultiply(Expression arg1, Expression arg2) {
		return new TwoArgOperator(arg1, arg2, Type.Long) {
			@Override
			public Value evaluate(Scope scope, ResourceCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
				counter.addTime(delay);
				return new Value(this.type, (Long)arg1.evaluate(scope, counter, inputScanner, outputWriter).value * (Long)arg2.evaluate(scope, counter, inputScanner, outputWriter).value);
			}
		};
	}

	public static TwoArgOperator newDivide(Expression arg1, Expression arg2) {
		return new TwoArgOperator(arg1, arg2, Type.Long) {
			@Override
			public Value evaluate(Scope scope, ResourceCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
				counter.addTime(delay);
				return new Value(this.type, (Long)arg1.evaluate(scope, counter, inputScanner, outputWriter).value / (Long)arg2.evaluate(scope, counter, inputScanner, outputWriter).value);
			}
		};
	}


	public static TwoArgOperator newModulo(Expression arg1, Expression arg2) {
		return new TwoArgOperator(arg1, arg2, Type.Long) {
			@Override
			public Value evaluate(Scope scope, ResourceCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
				counter.addTime(delay);
				return new Value(this.type, (Long)arg1.evaluate(scope, counter, inputScanner, outputWriter).value % (Long)arg2.evaluate(scope, counter, inputScanner, outputWriter).value);
			}
		};
	}

	public static TwoArgOperator newOr(Expression arg1, Expression arg2) {
		return new TwoArgOperator(arg1, arg2, Type.Long) {
			@Override
			public Value evaluate(Scope scope, ResourceCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
				counter.addTime(delay);
				boolean result = (Long)arg1.evaluate(scope, counter, inputScanner, outputWriter).value != 0 || (Long)arg2.evaluate(scope, counter, inputScanner, outputWriter).value != 0;
				return boolToLongVal(result);
			}
		};
	}

	public static TwoArgOperator newAnd(Expression arg1, Expression arg2) {
		return new TwoArgOperator(arg1, arg2, Type.Long) {
			@Override
			public Value evaluate(Scope scope, ResourceCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
				counter.addTime(delay);
				boolean result = (Long)arg1.evaluate(scope, counter, inputScanner, outputWriter).value != 0 && (Long)arg2.evaluate(scope, counter, inputScanner, outputWriter).value != 0;
				return boolToLongVal(result);
			}
		};
	}

	public static TwoArgOperator newLess(Expression arg1, Expression arg2) {
		return new TwoArgOperator(arg1, arg2, Type.Long) {
			@Override
			public Value evaluate(Scope scope, ResourceCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
				counter.addTime(delay);
				boolean result = (Long)arg1.evaluate(scope, counter, inputScanner, outputWriter).value < (Long)arg2.evaluate(scope, counter, inputScanner, outputWriter).value;
				return boolToLongVal(result);
			}
		};
	}

	public static TwoArgOperator newLessEqual(Expression arg1, Expression arg2) {
		return new TwoArgOperator(arg1, arg2, Type.Long) {
			@Override
			public Value evaluate(Scope scope, ResourceCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
				counter.addTime(delay);
				boolean result = (Long)arg1.evaluate(scope, counter, inputScanner, outputWriter).value <= (Long)arg2.evaluate(scope, counter, inputScanner, outputWriter).value;
				return boolToLongVal(result);
			}
		};
	}

	public static TwoArgOperator newGreater(Expression arg1, Expression arg2) {
		return new TwoArgOperator(arg1, arg2, Type.Long) {
			@Override
			public Value evaluate(Scope scope, ResourceCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
				counter.addTime(delay);
				boolean result = (Long)arg1.evaluate(scope, counter, inputScanner, outputWriter).value > (Long)arg2.evaluate(scope, counter, inputScanner, outputWriter).value;
				return boolToLongVal(result);
			}
		};
	}

	public static TwoArgOperator newGreaterEqual(Expression arg1, Expression arg2) {
		return new TwoArgOperator(arg1, arg2, Type.Long) {
			@Override
			public Value evaluate(Scope scope, ResourceCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
				counter.addTime(delay);
				boolean result =(Long)arg1.evaluate(scope, counter, inputScanner, outputWriter).value >= (Long)arg2.evaluate(scope, counter, inputScanner, outputWriter).value;
				return boolToLongVal(result);
			}
		};
	}

	public static TwoArgOperator newEqual(Expression arg1, Expression arg2) {
		return new TwoArgOperator(arg1, arg2, Type.Long) {
			@Override
			public Value evaluate(Scope scope, ResourceCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
				counter.addTime(delay);
				boolean result = arg1.evaluate(scope, counter, inputScanner, outputWriter).value.equals(arg2.evaluate(scope, counter, inputScanner, outputWriter).value);
				return boolToLongVal(result);
			}
		};
	}

	public static TwoArgOperator newNotEqual(Expression arg1, Expression arg2) {
		return new TwoArgOperator(arg1, arg2, Type.Long) {
			@Override
			public Value evaluate(Scope scope, ResourceCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
				counter.addTime(delay);
				boolean result =!arg1.evaluate(scope, counter, inputScanner, outputWriter).value.equals(arg2.evaluate(scope, counter, inputScanner, outputWriter).value);
				return boolToLongVal(result);
			}
		};
	}
}
