package mimuw.idlearn.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
	void simpleStateMachineTest(){
		val = 0;
		StateMachine stateMachine = new StateMachine();
		State state;
		
		stateMachine.add(new StateTest(1));
		assertEquals(val, 0);
		
		state = stateMachine.get();
		assertEquals(val, 1);
		
		stateMachine.add(new StateTest(2));
		stateMachine.add(new StateTest(3));
		
		assertEquals(((StateTest)state).get(), 1);
		
		stateMachine.get();
		assertEquals(val, 3);
		
		stateMachine.replace(new StateTest(4));
		assertEquals(val, 3);
		
		stateMachine.get();
		assertEquals(val, 4);
		
		stateMachine.pop();
		assertEquals(val, 4);
		
		stateMachine.get();
		assertEquals(val, 2);
		
		stateMachine.clear();
		assertEquals(val, 2);
		
		state = stateMachine.get();
		assertEquals(val, -1);
		assertNull(state);
	}
}
