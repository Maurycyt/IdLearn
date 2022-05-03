package mimuw.idlearn.scenes.preloader;

import mimuw.idlearn.core.Emitter;

public abstract class LoadTask {
	Emitter emitter;
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
