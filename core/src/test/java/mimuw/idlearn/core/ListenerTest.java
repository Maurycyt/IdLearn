package mimuw.idlearn.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ListenerTest{
	@Test
	public void testSimpleListener(){
		final Integer[] x = new Integer[1];
		
		Emitter emitter = new Emitter();
		Listener test = event -> {
			if(event.value() instanceof Integer)
				x[0] = (Integer)event.value();
		};
		emitter.connect(test);
		
		emitter.fire(6);
		Assertions.assertEquals(x[0], 6);
		
		emitter.fire(3);
		emitter.fire(1);
		Assertions.assertEquals(x[0], 1);
		
		emitter.notify(9);
		Assertions.assertEquals(x[0], 1);
		emitter.processEvents();
		Assertions.assertEquals(x[0], 9);

		emitter.notify(-4);
		emitter.notify(-3);
		Assertions.assertEquals(x[0], 9);
		emitter.processEvents();
		Assertions.assertEquals(x[0], -3);
		
		emitter.disconnect(test);
		
		emitter.fire(4);
		Assertions.assertEquals(x[0], -3);
		emitter.notify(4);
		emitter.processEvents();
		Assertions.assertEquals(x[0], -3);
	}
	
	@Test
	public void testMultipleListeners(){
		final Integer[] x = new Integer[1];
		
		Emitter emitter = new Emitter();
		Listener test1 = event -> {
			if(event.value() instanceof Integer)
				x[0] = (Integer)event.value();
		};
		Listener test2 = event -> {
			if(event.value() instanceof Integer)
				x[0] = (Integer)event.value() * 2;
		};
		
		emitter.connect(test1);
		emitter.connect(test2);
		
		emitter.fire(6);
		Assertions.assertTrue(x[0] == 6 || x[0] == 12);
		
		emitter.notify(-3);
		Assertions.assertTrue(x[0] == 6 || x[0] == 12);
		emitter.processEvents();
		
		Assertions.assertTrue(x[0] == -3 || x[0] == -6);
	}
}
