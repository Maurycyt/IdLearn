package mimuw.idlearn.scenes;

import javafx.stage.Stage;
//import mimuw.idlearn.core.*;
import mimuw.idlearn.core.Emitter;
import mimuw.idlearn.core.Event;
import mimuw.idlearn.core.Listener;
import mimuw.idlearn.scenes.preloader.LoadTask;
import mimuw.idlearn.scenes.preloader.Preloader;

import java.util.Stack;

// This class follows the singleton design pattern
public class SceneManager implements Listener {
	private static final SceneManager instance = new SceneManager();

	public static SceneManager getInstance() {
		return instance;
	}

	private SceneManager(){
		emitter.connect(this);
	}

	private void activateCurrent(){
		sceneRoots.peek().activate();
	}

	private void deactivateCurrent(){
		sceneRoots.peek().deactivate();
	}

	/**
	 * Add a sceneRoot to the sceneRoot machine
	 * @param sceneRoot new sceneRoot
	 */
	synchronized public void add(SceneRoot sceneRoot){
		emitter.notify((SceneRootEvent)() -> {
			if(!sceneRoots.empty())
				deactivateCurrent();
			sceneRoots.push(sceneRoot);
			activateCurrent();
		});
	}

	synchronized public void add(SceneRoot sceneRoot, LoadTask task) {
		add(sceneRoot);
		add(new Preloader(task));
	}

	synchronized public void replace(SceneRoot sceneRoot, LoadTask task) {
		replace(sceneRoot);
		add(new Preloader(task));
	}

	/**
	 * Remove top sceneRoot from the sceneRoot machine
	 */
	synchronized public void pop(){
		emitter.notify((SceneRootEvent)() -> {
			deactivateCurrent();
			sceneRoots.pop();
			if(!sceneRoots.empty())
				activateCurrent();
		});
	}

	/**
	 * Replace top sceneRoot with new sceneRoot
	 * @param sceneRoot new sceneRoot
	 */
	synchronized public void replace(SceneRoot sceneRoot){
		pop();
		add(sceneRoot);
	}

	/**
	 * Remove all sceneRoots from the sceneRoot machine
	 */
	synchronized public void clear(){
		emitter.notify((SceneRootEvent)() -> {
			while (!sceneRoots.empty()) {
				deactivateCurrent();
				sceneRoots.pop();
				if (!sceneRoots.empty())
					activateCurrent();
			}
		});
	}

	/**
	 * Execute queued events and return top sceneRoot
	 * @return current sceneRoot
	 */
	synchronized public SceneRoot get(){
		emitter.processEvents();
		if(sceneRoots.empty())
			return null;
		return sceneRoots.peek();
	}

	@Override
	synchronized public void onNotify(Event event){
		((SceneRootEvent)event.value()).run();
	}

	private final Stack<SceneRoot> sceneRoots = new Stack<>();
	private final Emitter emitter = new Emitter();

	private interface SceneRootEvent{
		void run();
	}
}
