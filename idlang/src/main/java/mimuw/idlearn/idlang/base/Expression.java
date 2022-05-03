package mimuw.idlearn.idlang.base;

import mimuw.idlearn.idlang.environment.Scope;
import mimuw.idlearn.idlang.exceptions.SimulationException;

public interface Expression<T> {
	double delay = 1;
	Value<T> evaluate(Scope scope, TimeCounter counter) throws SimulationException;
}
