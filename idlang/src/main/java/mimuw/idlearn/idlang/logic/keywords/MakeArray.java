package mimuw.idlearn.idlang.logic.keywords;

import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.ResourceCounter;
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
		expression.assertType(Type.Long);
		this.expression = expression;
		this.type = Null;
	}

	@Override
	public Value evaluate(Scope scope, ResourceCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
		Long size = (Long)expression.evaluate(scope, counter, inputScanner, outputWriter).value;

		counter.addTime(delay);
		counter.addMemory(size.intValue());

		Scope origin = scope.getOriginScope(name);

		ArrayList table = new ArrayList<Long>(size.intValue());
		for (int i = 0; i < size; i++) {
			table.add(0);
		}


		if (origin == null)
			scope.add(name, new Value(Type.Table, table));
		else
			origin.add(name, new Value(Type.Table, table));

		return new Value(Null, null);
	}
}
