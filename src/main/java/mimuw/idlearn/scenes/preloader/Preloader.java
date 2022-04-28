package mimuw.idlearn.scenes.preloader;

import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import mimuw.idlearn.core.Emitter;
import mimuw.idlearn.core.Event;
import mimuw.idlearn.core.Listener;
import mimuw.idlearn.scenes.Scene;
import mimuw.idlearn.scenes.SceneManager;

public class Preloader extends Scene implements Listener {
	private final ProgressBar bar;
	private final Emitter emitter;

	public Preloader(SceneManager sceneManager, LoadTask task) {
		super(sceneManager);

		emitter = new Emitter();
		bar = new ProgressBar();

		getChildren().add(bar);

		task.setEmitter(emitter);
		emitter.connect(this);

		Thread loader = new Thread(task::load);
		loader.setDaemon(true);
		loader.start();
	}

	@Override
	public void update(Duration time) {
		synchronized (emitter){
			emitter.processEvents();
		}
	}

	@Override
	public void handleEvent(javafx.event.Event event) {

	}

	@Override
	public void onNotify(Event event) {
		// TODO: fill with content
		if (event.type() == PreloaderEvent.class) {
			PreloaderEvent preloaderEvent = (PreloaderEvent) event.value();
			if (preloaderEvent.type() == PreloaderEvent.Type.Success) {
				bar.setProgress(1);
				getSceneManager().pop();
			} else if (preloaderEvent.type() == PreloaderEvent.Type.Progress) {
				bar.setProgress(preloaderEvent.progress());
			} else if (preloaderEvent.type() == PreloaderEvent.Type.Failure) {}
		}
	}
}
