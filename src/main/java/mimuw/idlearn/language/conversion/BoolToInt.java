package mimuw.idlearn.language.conversion;

import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.environment.Scope;

public class BoolToInt implements Expression<Integer> {

    private final Expression<Boolean> expression;

    public BoolToInt(Expression<Boolean> expression) {
        this.expression = expression;
    }

    @Override
    public Value<Integer> evaluate(Scope scope) throws RuntimeException {
        Value<Boolean> eval = expression.evaluate(scope);

        if (eval.getValue()) {
            return new Value<>(1);
        }
        else {
            return new Value<>(0);
        }
    }
}
