package mimuw.idlearn.scenes.preloader;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import mimuw.idlearn.core.Emitter;
import mimuw.idlearn.core.Event;
import mimuw.idlearn.core.Listener;
import mimuw.idlearn.scenes.SceneManager;

public class Preloader extends Group implements Listener {
	private final Emitter emitter;

	// if root is null, it means IOException happened during loading of the preloader from .fxml
	// otherwise it will be primary VBox from .fxml
	public Preloader(BorderPane root, LoadTask task) {
		emitter = new Emitter();

		emitter.connect(this);

		Thread loader = new Thread(() -> task.run(this));
		loader.setDaemon(true);
		loader.start();

		final Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);

		// Main loop, updates [framesPerSecond] times per second
		timeline.getKeyFrames().add(
				new KeyFrame(
						Duration.seconds(1.0 / 60),
						actionEvent -> {
							synchronized (emitter) {
								emitter.processEvents();
							}
						}));
		timeline.play();
	}

	Emitter getEmitter() {
		return emitter;
	}

	@Override
	public void onNotify(Event event) {
		// TODO: fill with content
		// HERE AND ONLY HERE update javafx, you can set it up in constructor, but only modify it here
		if (event.type() == PreloaderEvent.class) {
			PreloaderEvent preloaderEvent = (PreloaderEvent) event.value();
			if (preloaderEvent.type() == PreloaderEvent.Type.Success) {
				SceneManager.getInstance().pop();
			} else if (preloaderEvent.type() == PreloaderEvent.Type.Progress) {
			} else if (preloaderEvent.type() == PreloaderEvent.Type.Fail) {}
		}
	}
}

