package mimuw.idlearn;

import javafx.scene.Group;
import javafx.scene.Node;
import mimuw.idlearn.core.State;
import mimuw.idlearn.core.StateMachine;

import java.util.Collection;

public class Scene extends Group implements State{
	public Scene(StateMachine stateMachine_){
		this.stateMachine_ = stateMachine_;
	}
	
	public Scene(StateMachine stateMachine_, Node... nodes){
		super(nodes);
		this.stateMachine_ = stateMachine_;
	}
	
	public Scene(StateMachine stateMachine_, Collection<Node> collection){
		super(collection);
		this.stateMachine_ = stateMachine_;
	}
	
	@Override
	public void activate(){
		setVisible(true);
		setDisabled(false);
	}
	
	@Override
	public void deactivate(){
		setDisabled(true);
		setVisible(false);
	}
	
	public void update(){}
	
	protected final StateMachine stateMachine_;
}
