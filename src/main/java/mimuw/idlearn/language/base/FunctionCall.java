package mimuw.idlearn.language.base;

import mimuw.idlearn.language.environment.Scope;

import java.util.ArrayList;

public class FunctionCall extends Expression {
    private final String name;
    private final ArrayList<Expression> arguments;

    public FunctionCall(String name, ArrayList<Expression> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    @Override
    public Value evaluate(Scope scope) throws RuntimeException{
        return scope.getFunction(name).call(scope, arguments);
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        if(!super.equals(o))
            return false;

        FunctionCall other = (FunctionCall)o;

        return name.equals(other.name) && arguments.equals(other.arguments);
    }

    @Override
    public String toPrettyString(String indent){
        StringBuilder out = new StringBuilder()
                .append(name)
                .append("(");

        for (Expression arg : arguments){
            out.append(arg.toPrettyString(indent));
            if (!arg.equals(arguments.get(arguments.size() - 1)))
                out.append(", ");
        }

        return out
                .append(")")
                .toString();
    }
}
