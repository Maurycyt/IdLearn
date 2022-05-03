package mimuw.idlearn.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class StateMachineTest{
	private static int val;
	
	private static class StateTest implements State{
		private final int x;
		
		public StateTest(int x){
			this.x = x;
		}
		
		@Override
		public void activate(){
			val = x;
		}
		
		@Override
		public void deactivate(){
			val = -x;
		}
		
		public int get(){
			return x;
		}
	}

	@Test
	public void testSimpleStateMachine(){
		val = 0;
		StateMachine stateMachine = new StateMachine();
		State state;
		
		stateMachine.add(new StateTest(1));
		Assertions.assertEquals(val, 0);
		
		state = stateMachine.get();
		Assertions.assertEquals(val, 1);
		
		stateMachine.add(new StateTest(2));
		stateMachine.add(new StateTest(3));
		
		Assertions.assertEquals(((StateTest)state).get(), 1);
		
		stateMachine.get();
		Assertions.assertEquals(val, 3);
		
		stateMachine.replace(new StateTest(4));
		Assertions.assertEquals(val, 3);
		
		stateMachine.get();
		Assertions.assertEquals(val, 4);
		
		stateMachine.pop();
		Assertions.assertEquals(val, 4);
		
		stateMachine.get();
		Assertions.assertEquals(val, 2);
		
		stateMachine.clear();
		Assertions.assertEquals(val, 2);
		
		state = stateMachine.get();
		Assertions.assertEquals(val, -1);
		Assertions.assertNull(state);
	}
}
