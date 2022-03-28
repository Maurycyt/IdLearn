package mimuw.idlearn.language.keywords;

import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.language.base.Expression;

import java.util.ArrayList;

public class Block extends Expression {
    private final ArrayList<Expression> instructions;

    public Block(ArrayList<Expression> instructions) {
        this.instructions = instructions;
    }

    @Override
    public Value evaluate(Scope scope) throws RuntimeException {
        Scope nestedScope = new Scope(scope);
        Value value = new Value(0);
        for (Expression i : instructions) {
            value = i.evaluate(nestedScope);
        }
        return value; // the value of the block is that of the last instruction
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        if(!super.equals(o))
            return false;

        Block block = (Block)o;

        return instructions.equals(block.instructions);
    }

    @Override
    public int hashCode(){
        int result = super.hashCode();
        result = 31 * result + instructions.hashCode();
        return result;
    }

    @Override
    public String toPrettyString(String indent) {
        StringBuilder out = new StringBuilder()
            .append("{\n");
        for (Expression expression : instructions) {
            out
                .append(indent)
                .append(tabIndent)
                .append(expression.toPrettyString(indent + tabIndent))
                .append("\n");
        }
        return out
                .append(indent)
                .append("}")
                .toString();
    }
}
