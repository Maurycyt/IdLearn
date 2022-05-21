package mimuw.idlearn.idlang.logic.keywords;

import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.ResourceCounter;
import mimuw.idlearn.idlang.logic.base.Value;
import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static mimuw.idlearn.idlang.logic.base.Type.Null;

public class Block extends Expression {
	private final ArrayList<Expression> instructions;

	public Block(ArrayList<Expression> instructions) {
		this.type = Null;
		this.instructions = instructions;
	}

	public Block(Expression... instructions) {
		this.type = Null;
		this.instructions = new ArrayList<>(instructions.length);
		this.instructions.addAll(List.of(instructions));
	}

	@Override
	public Value evaluate(Scope scope, ResourceCounter counter, Scanner inputScanner, Writer outputWriter) throws SimulationException {
		for (Expression i : instructions) {
			i.evaluate(scope, counter, inputScanner, outputWriter);
		}
		return new Value(Null, null);
	}
}
