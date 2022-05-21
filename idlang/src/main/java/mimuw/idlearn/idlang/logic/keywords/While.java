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

public class While extends Expression {
	private final Expression condition;
	private final Block body;

	public While(Expression condition, Block body) {
		condition.assertType(Type.Long);
		this.type = Null;
		this.condition = condition;
		this.body = body;
	}

	@Override
	public Value evaluate(Scope scope, TimeCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
		while ((Long)condition.evaluate(scope, counter, inputScanner, outputWriter).value != 0) {
			counter.addTime(delay);
			body.evaluate(new Scope(scope), counter, inputScanner, outputWriter);
		}
		return new Value(Null, null);
	}
}
