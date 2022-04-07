package mimuw.idlearn.scenes;

import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import mimuw.idlearn.core.Emitter;
import mimuw.idlearn.core.Event;
import mimuw.idlearn.core.Listener;
import mimuw.idlearn.core.StateMachine;
import mimuw.idlearn.scenes.events.PreloaderEvent;

public class Preloader extends Scene implements Listener {
	public static abstract class LoadTask {
		private Emitter emitter;
		protected void logProgress(double progress){
			synchronized (emitter) {
				emitter.notify(PreloaderEvent.Progress(progress));
			}
		}
		protected void logSuccess(){
			synchronized (emitter) {
				emitter.notify(PreloaderEvent.Success());
			}
		}
		protected void logFail(){
			synchronized (emitter) {
				emitter.notify(PreloaderEvent.Fail());
			}
		}
		public abstract void load();
	}

	private final ProgressBar bar;
	private final Emitter emitter;

	public Preloader(StateMachine stateMachine, LoadTask task) {
		super(stateMachine);

		emitter = new Emitter();
		bar = new ProgressBar();

		getChildren().add(bar);

		task.emitter = emitter;
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
	public void onNotify(Event event) {
		// TODO: fill with content
		if (event.type() == PreloaderEvent.class) {
			PreloaderEvent preloaderEvent = (PreloaderEvent) event.value();
			if (preloaderEvent.type() == PreloaderEvent.Type.Success) {
				bar.setProgress(1);
				stateMachine.pop();
			} else if (preloaderEvent.type() == PreloaderEvent.Type.Progress) {
				bar.setProgress(preloaderEvent.progress());
			} else if (preloaderEvent.type() == PreloaderEvent.Type.Fail) {}
		}
	}
}
