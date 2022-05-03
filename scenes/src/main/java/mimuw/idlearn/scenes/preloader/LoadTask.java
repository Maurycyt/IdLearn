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
		preloader.onLogProgress(progress);
	}

	/**
	 * Log that task finished successfully
	 */
	protected void logSuccess(){
		preloader.onSuccess();
	}

	/**
	 * Log that task failed its execution
	 */
	protected void logFail(){
		preloader.onFail();
	}

	/**
	 * Task to execute
	 */
	public abstract void load();
}
