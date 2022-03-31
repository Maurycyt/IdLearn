package mimuw.idlearn.language.keywords;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;

import java.util.ArrayList;

public class Block<Void> implements Expression<Void> {
    private final ArrayList<Expression<?>> instructions;

    public Block(ArrayList<Expression<?>> instructions) {
        this.instructions = instructions;
    }

    @Override
    public Value<Void> evaluate(Scope scope) throws RuntimeException {
        Scope nestedScope = new Scope(scope);
        for (Expression<?> i : instructions) {
            i.evaluate(nestedScope);
        }
        return new Value<>(null); // the value of the block is that of the last instruction
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        if(!super.equals(o))
            return false;

        Block<Void> block = (Block<Void>)o;

        return instructions.equals(block.instructions);
    }

    @Override
    public int hashCode(){
        int result = super.hashCode();
        result = 31 * result + instructions.hashCode();
        return result;
    }

}
