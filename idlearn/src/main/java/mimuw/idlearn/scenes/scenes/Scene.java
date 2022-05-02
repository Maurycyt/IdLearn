package mimuw.idlearn.scenes;

import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Duration;
import mimuw.idlearn.core.State;
import mimuw.idlearn.scenes.preloader.Preloader;
import mimuw.idlearn.scenes.preloader.LoadTask;

import java.util.Collection;

/**
 * class representing application's scene and works just as javafx.Group
 * 
 * for more information, see:
 * - https://gitlab.com/Maurycyt/idlearn/-/wikis/Scene
 * - https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Group.html
 */
public abstract class Scene extends Group implements State{
	public Scene(SceneManager sceneManager) {
		this.sceneManager = sceneManager;
	}

	public Scene(SceneManager sceneManager, Node... nodes) {
		super(nodes);
		this.sceneManager = sceneManager;
	}

	public Scene(Collection<Node> collection, SceneManager sceneManager) {
		super(collection);
		this.sceneManager = sceneManager;
	}

	public void load(LoadTask task){
		getSceneManager().add(new Preloader(getSceneManager(), task));
	}

	@Override
	public void activate(){
		//setVisible(true);
		//setDisabled(false);
	}

	@Override
	public void deactivate(){
		//setDisabled(true);
		//setVisible(false);
	}

	public boolean isExit() {
		return exit;
	}

	public void exit() {
		exit = true;
	}

	public abstract void handleEvent(Event event);

	public abstract void update(Duration time);

	public SceneManager getSceneManager() {
		return sceneManager;
	}

	private final SceneManager sceneManager;
	private boolean exit = false;
}
