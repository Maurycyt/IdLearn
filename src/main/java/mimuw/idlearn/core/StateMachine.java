package mimuw.idlearn.core;

import java.util.Stack;

/**
 * Implementation of a State Machine design pattern.
 * Allows for safe managment of states.
 * 
 * Each operation on State Machine is lazy. 
 * Actions only take place once the 'get' function is executed.
 */
public class StateMachine implements Listener{
	public StateMachine(){
		emitter.connect(this);
	}
	
	/**
	 * Add a state to the state machine
	 * @param state new state
	 */
	public void add(State state){
		emitter.notify((StateEvent)() -> {
			if(!states.empty())
				states.peek().deactivate();
			states.push(state);
			states.peek().activate();
		});
	}
	
	/**
	 * Remove top state from the state machine
	 */
	public void pop(){
		emitter.notify((StateEvent)() -> {
			states.pop().deactivate();
			if(!states.empty())
				states.peek().activate();
		});
	}
	
	/**
	 * Replace top state with new state
	 * @param state new state
	 */
	public void replace(State state){
		pop();
		add(state);
	}
	
	/**
	 * Remove all states from the state machine
	 */
	public void clear(){
		emitter.notify((StateEvent)() -> {
			while(!states.empty()){
				states.pop().deactivate();
				if(!states.empty())
					states.peek().activate();
			}
		});
	}
	
	/**
	 * Execute queued events and return top state
	 * @return current state
	 */
	public State get(){
		emitter.processEvents();
		if(states.empty())
			throw new RuntimeException("No active State in StateMachine");
		return states.peek();
	}
	
	@Override
	public void onNotify(Event event){
		((StateEvent)event.value()).run();
	}
	
	private final Stack<State> states = new Stack<>();
	private final Emitter emitter = new Emitter();
	
	private interface StateEvent{
		void run();
	}
}
