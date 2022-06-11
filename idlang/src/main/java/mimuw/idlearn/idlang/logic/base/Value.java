package mimuw.idlearn.idlang.logic.base;

public class Value {
	public Object value;
	public Type type;

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
		switch (type) {
			case Long -> {
				return ((Long)value).toString();
			}
			default -> throw new AssertionError("Impossible data type - backend somehow allows writing something imparsable");

		}
	}
}
