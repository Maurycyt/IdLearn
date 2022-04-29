package mimuw.idlearn.scenes;

import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.Collection;

/**
 * class representing application's scene and works just as javafx.Group
 * 
 * for more information, see:
 * - https://gitlab.com/Maurycyt/idlearn/-/wikis/Scene
 * - https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Group.html
 */
public class SceneRoot extends Group{
	private boolean exit = false;

	public SceneRoot() {
	}

	public SceneRoot(Node... nodes) {
		super(nodes);
	}

	public SceneRoot(Collection<Node> collection) {
		super(collection);
	}

	public void activate(){}

	public void deactivate(){}

	public boolean isExit() {
		return exit;
	}

	public void exit() {
		exit = true;
	}

	public void handleEvent(Event event){}

	public void update(Duration time){}
}
