package mimuw.idlearn.scenes.preloader;

import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import mimuw.idlearn.core.Emitter;
import mimuw.idlearn.core.Event;
import mimuw.idlearn.core.Listener;
import mimuw.idlearn.scenes.SceneManager;
import mimuw.idlearn.scenes.SceneRoot;

public class Preloader extends SceneRoot implements Listener {
	private final ProgressBar bar;
	private final Emitter emitter;

	public Preloader(LoadTask task) {
		this.bar = new ProgressBar();
		this.getChildren().add(bar);

		emitter = new Emitter();
		task.setEmitter(emitter);
		emitter.connect(this);

		Thread loader = new Thread(task::load);
		loader.setDaemon(true);
		loader.start();
	}

	public void update(Duration time) {
		synchronized (emitter) {
			emitter.processEvents();
		}
	}

	//TODO?
	@Override
	public void onNotify(Event event) {
		if (event.type() == PreloaderEvent.class) {
			PreloaderEvent preloaderEvent = (PreloaderEvent) event.value();
			switch (preloaderEvent.type()) {
				case Success:
					bar.setProgress(1);
					SceneManager.getInstance().pop();
					break;
				case Progress:
					bar.setProgress(preloaderEvent.progress());
					break;
				case Failure:
					break;
			}
		}
	}
}
