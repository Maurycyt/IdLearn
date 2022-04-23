package mimuw.idlearn.scenes;

import mimuw.idlearn.core.StateMachine;
import mimuw.idlearn.scenes.preloader.LoadTask;

public class SceneManager extends StateMachine {
	public void add(Scene scene, LoadTask task){
		add(scene);
		scene.load(task);
	}

	public Scene get(){
		return (Scene)super.get();
	}
}
