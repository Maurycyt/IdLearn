package mimuw.idlearn.language.base;

import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.language.exceptions.SimulationException;

public interface Expression<T> {
	double delay = 1;
	Value<T> evaluate(Scope scope, TimeCounter counter) throws SimulationException;
}
