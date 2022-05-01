package mimuw.idlearn.language.keywords;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.TimeCounter;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.language.exceptions.SimulationException;

import java.util.ArrayList;
import java.util.List;

public class Block implements Expression<Void> {
	private final ArrayList<Expression<?>> instructions;

	public Block(ArrayList<Expression<?>> instructions) {
		this.instructions = instructions;
	}

	public Block(Expression<?>... instructions) {
		this.instructions = new ArrayList<>(instructions.length);
		this.instructions.addAll(List.of(instructions));
	}

	@Override
	public Value<Void> evaluate(Scope scope, TimeCounter counter) throws SimulationException {
		for (Expression<?> i : instructions) {
			i.evaluate(scope, counter);
		}
		return new Value<>(null);
	}
}
