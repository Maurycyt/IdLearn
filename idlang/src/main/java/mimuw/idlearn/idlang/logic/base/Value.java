package mimuw.idlearn.idlang.logic.base;

public class Value {
	public final Object value;
	public final Type type;

	public Value(Type type, Object value) {
		this.type = type;
		this.value = value;
	}
	public Value(Integer integer) {
		this.type = Type.Long;
		this.value = integer.longValue();
	}
	public Value(Long longValue) {
		this.type = Type.Long;
		this.value = longValue;
	}

	@Override
	public String toString() {
		if (type == Type.Long) {
			return ((Long) value).toString();
		}
		throw new AssertionError("Impossible data type - backend somehow allows writing something imparsable");
	}
}
