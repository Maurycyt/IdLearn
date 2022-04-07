package mimuw.idlearn.scenes.events;

import mimuw.idlearn.core.Event;

/**
 * Event used in Preloader scene
 */
public class PreloaderEvent {
	public enum Type {
		Success,
		Progress,
		Fail
	}
	private Type type;
	private Double progress;

	public Type type(){
		return type;
	}

	public double progress(){
		return progress;
	}

	/**
	 * Create a success event. It is intended to indicate the end of loading process.
	 * @return Success PreloaderEvent
	 */
	public static PreloaderEvent Success(){
		PreloaderEvent event = new PreloaderEvent();
		event.type = Type.Success;
		return event;
	}

	/**
	 * Create a progress event. It should mark ongoing progress of loading process.
	 * @param progress - progress in percents
	 * @return Progress PreloaderEvent
	 */
	public static PreloaderEvent Progress(double progress){
		PreloaderEvent event = new PreloaderEvent();
		event.type = Type.Progress;
		event.progress = progress;
		return event;
	}

	/**
	 * Create a fail event. It should notify about some kind of failure during loading process.
	 * @return Fail PreloaderEvent
	 */
	public static PreloaderEvent Fail(){
		PreloaderEvent event = new PreloaderEvent();
		event.type = Type.Fail;
		return event;
	}
}
