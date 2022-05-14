package mimuw.idlearn.scenes.preloader;

public abstract class LoadTask {
	private Preloader preloader;

	/**
	 * Internal execution of this task
	 * @param preloader preloader that executed this task
	 */
	void run(Preloader preloader) {
		this.preloader = preloader;
		load();
	}

	/**
	 * Log that some progress has been made
	 * @param progress total progress in percents
	 */
	protected void logProgress(double progress){
		synchronized (preloader.getEmitter()) {
			preloader.getEmitter().notify(PreloaderEvent.Progress(progress));
		}
	}

	/**
	 * Log that task finished successfully
	 */
	protected void logSuccess(){
		synchronized (preloader.getEmitter()) {
			preloader.getEmitter().notify(PreloaderEvent.Success());
		}
	}

	/**
	 * Log that task failed its execution
	 */
	protected void logFail(){
		synchronized (preloader.getEmitter()) {
			preloader.getEmitter().notify(PreloaderEvent.Fail());
		}
	}

	/**
	 * Task to execute
	 */
	public abstract void load();
}
