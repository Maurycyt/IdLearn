package mimuw.idlearn.idlang.logic.keywords;

import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.TimeCounter;
import mimuw.idlearn.idlang.logic.base.Type;
import mimuw.idlearn.idlang.logic.base.Value;
import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

import static mimuw.idlearn.idlang.logic.base.Type.Null;

public class SetArray extends Expression {

	private final Expression tableExpression;
	private final Expression indexExpression;
	private final Expression valueExpression;

	public SetArray(Expression tableExpression, Expression indexExpression, Expression valueExpression) {
		tableExpression.assertType(Type.Table);
		indexExpression.assertType(Type.Integer);
		valueExpression.assertType(Type.Integer);
		this.tableExpression = tableExpression;
		this.indexExpression = indexExpression;
		this.valueExpression = valueExpression;
		this.type = Null;
	}

	@Override
	public Value evaluate(Scope scope, TimeCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
		Value tableVal = tableExpression.evaluate(scope, counter, inputScanner, outputWriter);
		Value indexVal = indexExpression.evaluate(scope, counter, inputScanner, outputWriter);
		Value valueVal = valueExpression.evaluate(scope, counter, inputScanner, outputWriter);

		ArrayList<Integer> table = (ArrayList<Integer>) tableVal.value;
		table.set((Integer)indexVal.value, (Integer)valueVal.value);

		return new Value(Null, null);
	}
}
