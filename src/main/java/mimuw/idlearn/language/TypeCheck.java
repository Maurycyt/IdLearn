package mimuw.idlearn.language;

import java.util.Arrays;

public class TypeCheck{
	public static void assertType(Object object, Class<?>... types) throws RuntimeException{
		for(Class<?> type : types){
			if (isType(object, type))
				return;
		}
		throw new RuntimeException("Object has type " + object.getClass().getName() + ", " + Arrays.toString(types) + " required");
	}
	
	public static boolean isType(Object object, Class<?> type){
		return type.isAssignableFrom(object.getClass());
	}
}
