package mimuw.idlearn.scenes;

import javafx.scene.Parent;
import javafx.stage.Stage;
import mimuw.idlearn.scenes.preloader.LoadTask;
import mimuw.idlearn.scenes.preloader.Preloader;

import java.util.Stack;

// This class follows the singleton design pattern
public class SceneManager {
	private static final SceneManager instance = new SceneManager();

	public static SceneManager getInstance() {
		return instance;
	}

	private void updateStage(){
		if (stage != null)
			stage.getScene().setRoot(sceneRoots.peek());
	}

	/**
	 * Add a sceneRoot to the sceneRoot machine
	 * @param sceneRoot new sceneRoot
	 */
	synchronized public void push(Parent sceneRoot){
		sceneRoots.push(sceneRoot);
		updateStage();
	}

	/**
	 * Add a sceneRoot to the sceneRoot machine and execute a task in the background
	 * @param sceneRoot new sceneRoot
	 * @param task task to execute in the background
	 */
	synchronized public void push(Parent sceneRoot, LoadTask task) {
		sceneRoots.push(sceneRoot);
		sceneRoots.push(SceneUtils.createPreloader(task));
		updateStage();

	}

	/**
	 * Replace top sceneRoot with new sceneRoot
	 * @param sceneRoot new sceneRoot
	 */
	synchronized public void replace(Parent sceneRoot){
		sceneRoots.pop();
		sceneRoots.push(sceneRoot);
		updateStage();
	}

	/**
	 * Replace top sceneRoot with new sceneRoot and execute a task in the background
	 * @param sceneRoot new sceneRoot
	 * @param task task to execute in the background
	 */
	synchronized public void replace(Parent sceneRoot, LoadTask task) {
		sceneRoots.pop();
		sceneRoots.push(sceneRoot);
		sceneRoots.push(new Preloader(task));
		updateStage();
	}

	/**
	 * Remove top sceneRoot from the sceneRoot machine
	 */
	synchronized public void pop(){
		sceneRoots.pop();
		updateStage();
	}

	/**
	 * Remove all sceneRoots from the sceneRoot machine
	 */
	synchronized public void clear(){
		sceneRoots.clear();
	}

	/**
	 * Execute queued events and return top sceneRoot
	 * @return current sceneRoot
	 */
	synchronized public Parent get(){
		return sceneRoots.peek();
	}

	/**
	 * Close the game
	 */
	synchronized public void exit() {
		stage.close();
	}

	/**
	 * Set's the stage on which scenes appear
	 * @param stage stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	private final Stack<Parent> sceneRoots = new Stack<>();
	private Stage stage;
}
