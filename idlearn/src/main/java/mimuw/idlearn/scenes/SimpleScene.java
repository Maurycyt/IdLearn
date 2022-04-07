package mimuw.idlearn.scenes;

import javafx.scene.Node;
import javafx.util.Duration;
import mimuw.idlearn.core.StateMachine;

import java.util.Collection;

/**
 * Class for creating simple scenes just with collection of nodes
 */
public final class SimpleScene extends Scene{
	public SimpleScene(StateMachine stateMachine) {
		super(stateMachine);
	}

	public SimpleScene(StateMachine stateMachine, Node... nodes) {
		super(stateMachine, nodes);
	}

	public SimpleScene(StateMachine stateMachine, Collection<Node> collection) {
		super(stateMachine, collection);
	}

	@Override
	public void update(Duration time) {}
}
