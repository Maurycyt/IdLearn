package mimuw.idlearn.language.base;

import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.problems.ProblemPackage;
import java.io.EOFException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class IOHandler {
    private final static String whitespacePrefix = " ";
    private final ProblemPackage pkg;

    public IOHandler(ProblemPackage problem) {
        this.pkg = problem;
    }

    @SafeVarargs
    public final void takeInput(Scope scope, Variable<Integer>... in) throws EOFException {
        Scanner scanner =  pkg.getTestInputScanner();
        for (var v : in) {
            if (!scanner.hasNextInt()) {
                throw new EOFException("Too many variables provided for loading input.");
            }
            int value = scanner.nextInt();
            scope.add(v.getName(), new Value<>(value));
        }
    }

    public void giveOutput(String out) throws IOException {
        pkg.getTestOutputWriter().write(out);
    }

    public void giveOutput(Value<?> ...out) throws IOException {
        FileWriter writer = pkg.getTestOutputWriter();
        for (var v : out) {
            writer.write(whitespacePrefix + v.getValue());
        }
        writer.flush();
    }
}
