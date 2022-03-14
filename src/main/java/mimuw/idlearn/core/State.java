package mimuw.idlearn.core;

public abstract class State{
	State(StateMachine stateMachine){
		stateMachine_ = stateMachine;
	}
	
	public abstract void activate();
	public abstract void deactivate();
	
	protected StateMachine getStateMachine(){
		return stateMachine_;
	}
	private final StateMachine stateMachine_;
}
