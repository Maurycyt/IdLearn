package mimuw.idlearn.scenes.preloader;

import javafx.scene.Group;
import javafx.scene.control.ProgressBar;
import mimuw.idlearn.scenes.SceneManager;

public class Preloader extends Group {
	private final ProgressBar bar;

	public Preloader(LoadTask task) {
		this.bar = new ProgressBar();
		this.getChildren().add(bar);

		Thread loader = new Thread(() -> task.run(this));
		loader.setDaemon(true);
		loader.start();
	}

	void onSuccess() {
		bar.setProgress(1);
		SceneManager.getInstance().pop();
	}

	void onLogProgress(double progress) {
		bar.setProgress(progress);
	}

	void onFail() {

	}
}
