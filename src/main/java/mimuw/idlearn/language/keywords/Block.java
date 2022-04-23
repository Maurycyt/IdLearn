package mimuw.idlearn.language.keywords;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.TimeCounter;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;

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
	public Value<Void> evaluate(Scope scope, TimeCounter counter) throws RuntimeException {
		for (Expression<?> i : instructions) {
			i.evaluate(scope, counter);
		}
		return new Value<>(null);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;

		Block block = (Block) o;

		return instructions.equals(block.instructions);
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + instructions.hashCode();
		return result;
	}

}
