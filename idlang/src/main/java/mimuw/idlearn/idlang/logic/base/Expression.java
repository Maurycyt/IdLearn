package mimuw.idlearn.idlang.logic.base;

import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;

public interface Expression<T> {
	double delay = 1;
	Value<T> evaluate(Scope scope, TimeCounter counter) throws SimulationException;
}
