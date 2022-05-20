package mimuw.idlearn.idlang.logic.keywords;

import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.TimeCounter;
import mimuw.idlearn.idlang.logic.base.Type;
import mimuw.idlearn.idlang.logic.base.Value;
import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;

import java.io.Writer;
import java.util.Scanner;

import static mimuw.idlearn.idlang.logic.base.Type.Null;

public class If extends Expression {
	private final Expression condition;
	private final Block onTrue;
	private final Block onFalse;

	public If(Expression condition, Block onTrue, Block onFalse) {
		condition.assertType(Type.Integer);
		this.type = Null;
		this.condition = condition;
		this.onTrue = onTrue;
		this.onFalse = onFalse;
	}

	public If(Expression condition, Block onTrue) {
		condition.assertType(Type.Integer);
		this.type = Null;
		this.condition = condition;
		this.onTrue = onTrue;
		this.onFalse = null;
	}

	@Override
	public Value evaluate(Scope scope, TimeCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
		Value condEvaluation = condition.evaluate(scope, counter, inputScanner, outputWriter);
		counter.addTime(delay);

		if ((Integer)condEvaluation.value != 0)
			return onTrue.evaluate(new Scope(scope), counter, inputScanner, outputWriter);
		if (onFalse != null)
			return onFalse.evaluate(new Scope(scope), counter, inputScanner, outputWriter);
		return new Value(Null, null);
	}
}
