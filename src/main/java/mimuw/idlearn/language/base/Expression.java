package mimuw.idlearn.language.base;

import mimuw.idlearn.language.environment.Scope;

public interface Expression<T> {
	Value<T> evaluate(Scope scope) throws RuntimeException;
}
