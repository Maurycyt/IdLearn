package mimuw.idlearn;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Duration;
import mimuw.idlearn.core.State;
import mimuw.idlearn.core.StateMachine;

import java.util.Collection;

/**
 * class representing application's scene and works just as javafx.Group
 * 
 * for more information, see:
 * - https://gitlab.com/Maurycyt/idlearn/-/wikis/Scene
 * - https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Group.html
 */
public class Scene extends Group implements State{
	public Scene(StateMachine stateMachine){
		this.stateMachine = stateMachine;
	}
	
	public Scene(StateMachine stateMachine, Node... nodes){
		super(nodes);
		this.stateMachine = stateMachine;
	}
	
	public Scene(StateMachine stateMachine, Collection<Node> collection){
		super(collection);
		this.stateMachine = stateMachine;
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
	
	public void update(Duration time){}
	
	protected final StateMachine stateMachine;
}
