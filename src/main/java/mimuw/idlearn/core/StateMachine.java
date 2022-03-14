package mimuw.idlearn.core;

import java.util.Stack;

public class StateMachine implements Listener{
	StateMachine(){
		emitter.connect(this);
	}
	
	public void add(State state){
		emitter.notify((StateEvent)() -> {
			if(!states.empty())
				states.peek().deactivate();
			states.push(state);
			states.peek().activate();
		});
	}
	
	public void pop(){
		emitter.notify((StateEvent)() -> {
			states.pop().deactivate();
			if(!states.empty())
				states.peek().activate();
		});
	}
	
	public void replace(State state){
		pop();
		add(state);
	}
	
	public void clear(){
		emitter.notify((StateEvent)() -> {
			while(!states.empty()){
				states.pop().deactivate();
				if(!states.empty())
					states.peek().activate();
			}
		});
	}
	
	State get(){
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
