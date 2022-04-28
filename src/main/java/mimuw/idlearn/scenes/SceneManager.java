package mimuw.idlearn.scenes;

import mimuw.idlearn.core.StateMachine;
import mimuw.idlearn.scenes.preloader.LoadTask;

public class SceneManager extends StateMachine {
	public void add(Scene scene, LoadTask task){
		super.add(scene);
		scene.load(task);
	}

	public void replace(Scene scene, LoadTask task){
		super.replace(scene);
		scene.load(task);
	}

	public Scene get(){
		return (Scene)super.get();
	}
}
