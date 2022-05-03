package mimuw.idlearn.scenes;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.Collection;

public class SimpleScene extends Scene{
	public SimpleScene(SceneManager sceneManager) {
		super(sceneManager);
	}

	public SimpleScene(SceneManager sceneManager, Node... nodes) {
		super(sceneManager, nodes);
	}

	public SimpleScene(Collection<Node> collection, SceneManager sceneManager) {
		super(collection, sceneManager);
	}

	@Override
	public void update(Duration time) {}

	@Override
	public void handleEvent(Event event) {}
}
