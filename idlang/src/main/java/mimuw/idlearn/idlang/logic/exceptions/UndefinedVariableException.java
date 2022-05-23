package mimuw.idlearn.idlang.logic.exceptions;

public class UndefinedVariableException extends SimulationException {
	private final String varName;

	public UndefinedVariableException(String varName) {
		super("Variable '" + varName + "' not defined.");
		this.varName = varName;
	}

	public String getVarName() {
		return varName;
	}
}
