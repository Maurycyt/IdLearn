package mimuw.idlearn.core;

public record Event(Object value, Class<?> type){
	public Event(Object value){
		this(value, value.getClass());
	}
}
