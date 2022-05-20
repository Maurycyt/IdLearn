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

public class MakeArray extends Expression {
	private final String name;
	private final Expression expression;

	public MakeArray(String name, Expression expression) {
		this.name = name;
		expression.assertType(Type.Integer);
		this.expression = expression;
		this.type = Null;
	}

	@Override
	public Value evaluate(Scope scope, TimeCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
		Integer size = (Integer)expression.evaluate(scope, counter, inputScanner, outputWriter).value;

		Scope origin = scope.getOriginScope(name);

		ArrayList table = new ArrayList<Integer>(size);
		for (int i = 0; i < size; i++) {
			table.add(0);
		}

		Value tableVal = new Value(Type.Table, table);

		if (origin == null)
			scope.add(name, new Value(Type.Table, tableVal));
		else
			origin.add(name, new Value(Type.Table, tableVal));

		return new Value(Null, null);
	}
}
