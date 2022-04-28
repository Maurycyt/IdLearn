package mimuw.idlearn.scenes.preloader;

import mimuw.idlearn.core.Emitter;

public abstract class LoadTask {
	private Emitter emitter;

	public void setEmitter(Emitter emitter) {
		this.emitter = emitter;
	}

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
			emitter.notify(PreloaderEvent.Failure());
		}
	}
	public abstract void load();
}
