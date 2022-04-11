package mimuw.idlearn.language.base;

import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.problems.ProblemPackage;

import java.io.FileWriter;
import java.io.IOException;

public class OutputHandler implements Expression<Void> {
    private final static String separator = " ";
    private final ProblemPackage pkg;
    private Value<?> [] values;

    public OutputHandler(ProblemPackage pkg, Value<?> ...values) {
        this.pkg = pkg;
        this.values = values;
    }

    public void takeValues(Value<?> ...variables) {
        this.values = variables;
    }

    @Override
    public Value<Void> evaluate(Scope scope) throws RuntimeException {
        FileWriter writer = pkg.getTestOutputWriter();
        try {
            for (var v : values) {
                writer.write(separator + v.getValue());
            }
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Value<>(null);
    }
}
