package mimuw.idlearn.language.base;

import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.language.keywords.Block;

import java.util.ArrayList;

public class Function extends Expression {
    private final String name;
    private final ArrayList<String> argsNames;
    private final Block body;
    private final boolean local = false;
    private Scope baseScope;

    public Function(String name, ArrayList<String> argsNames, Block body, Scope baseScope) {
        this.name = name;
        this.argsNames = argsNames;
        this.body = body;
        this.baseScope = baseScope;
    }

    @Override
    public Value evaluate(Scope scope) throws RuntimeException{

        if(local){
            scope.add(name, this);
            baseScope = new Scope(scope);
        }
        else{
            scope.getGlobalScope().add(name, this);
            baseScope = new Scope(scope.getGlobalScope());
        }

        return new Value(0);
    }

    public Value call(Scope scope, Expression... args) throws RuntimeException{
        if(argsNames.size() != args.length)
            throw new RuntimeException("Invalid function call: " + name + " requires " + argsNames.size() + " arguments, " + args.length + " provided");

        Scope callScope = new Scope(baseScope);

        for(int i = 0; i < args.length; i++)
            callScope.add(argsNames.get(i), args[i].evaluate(scope));

        return body.evaluate(callScope);
    }

    public Value call(Scope scope, ArrayList<Expression> args) throws RuntimeException{
        if(argsNames.size() != args.size())
            throw new RuntimeException("Invalid function call: " + name + " requires " + argsNames.size() + " arguments, " + args.size() + " provided");

        Scope callScope = new Scope(baseScope);

        for(int i = 0; i < args.size(); i++)
            callScope.add(argsNames.get(i), args.get(i).evaluate(scope));

        return body.evaluate(callScope);
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;

        Function other = (Function)o;

        return local == other.local && name.equals(other.name) && argsNames.equals(other.argsNames) && body.equals(other.body);
    }

    @Override
    public int hashCode(){
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + argsNames.hashCode();
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (local ? 1 : 0);
        return result;
    }

    @Override
    public String toPrettyString(String indent){
        StringBuilder out = new StringBuilder();
        if (local)
            out.append("local ");
        out
                .append("fun ")
                .append(name)
                .append("(");

        for (String arg : argsNames){
            out.append(arg);
            if (!arg.equals(argsNames.get(argsNames.size() - 1)))
                out.append(", ");
        }
        return out
                .append(")")
                .append(body.toPrettyString(indent))
                .toString();
    }
}
