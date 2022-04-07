package mimuw.idlearn.scenes;

import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import mimuw.idlearn.core.Emitter;
import mimuw.idlearn.core.Event;
import mimuw.idlearn.core.Listener;
import mimuw.idlearn.core.StateMachine;
import mimuw.idlearn.scenes.events.PreloaderEvent;

/**
 * Special scene used to safely load resources in another thread.
 */
public class Preloader extends Scene implements Listener {
	/**
	 * A function wrapper allowing for logging progress of loading task
	 */
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
	private final Scene next;

	/**
	 *
	 * @param stateMachine
	 * @param task
	 * @param next
	 */
	public Preloader(StateMachine stateMachine, LoadTask task, Scene next) {
		super(stateMachine);

		emitter = new Emitter();
		bar = new ProgressBar();
		this.next = next;

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
	public void onNotify(Event event) { // will be only executed under synchronized in update method
		// TODO: fill with content
		if (event.type() == PreloaderEvent.class) {
			PreloaderEvent preloaderEvent = (PreloaderEvent) event.value();
			if (preloaderEvent.type() == PreloaderEvent.Type.Success) {
				bar.setProgress(1);
				stateMachine.replace(next);
			} else if (preloaderEvent.type() == PreloaderEvent.Type.Progress) {
				bar.setProgress(preloaderEvent.progress());
			} else if (preloaderEvent.type() == PreloaderEvent.Type.Fail) {}
		}
	}
}
