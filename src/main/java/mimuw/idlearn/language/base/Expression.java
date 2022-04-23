package mimuw.idlearn.language.base;

import mimuw.idlearn.language.environment.Scope;

public interface Expression<T> {
	static final double delay = 1;
	Value<T> evaluate(Scope scope, TimeCounter counter) throws RuntimeException;
}
