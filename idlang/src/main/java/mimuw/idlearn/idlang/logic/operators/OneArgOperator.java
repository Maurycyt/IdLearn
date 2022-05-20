package mimuw.idlearn.idlang.logic.operators;

import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.TimeCounter;
import mimuw.idlearn.idlang.logic.base.Type;
import mimuw.idlearn.idlang.logic.base.Value;
import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;

import java.io.Writer;
import java.util.Scanner;

public abstract class OneArgOperator extends Expression {
	protected Expression arg;

	private OneArgOperator(Expression arg, Type resultType) {
		this.arg = arg;
		this.type = resultType;
	}

	public static OneArgOperator newNot(Expression arg) {
		arg.assertType(Type.Integer);
		return new OneArgOperator(arg, Type.Integer) {
			@Override
			public Value evaluate(Scope scope, TimeCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
				counter.addTime(delay);
				Value value = arg.evaluate(scope, counter, inputScanner, outputWriter);
				if ((Integer)value.value == 0) {
					return new Value(Type.Integer, 1);
				}
				else {
					return new Value(Type.Integer, 0);
				}
			}
		};
	}
}
