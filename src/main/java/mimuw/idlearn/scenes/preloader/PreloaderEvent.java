package mimuw.idlearn.scenes.preloader;

public class PreloaderEvent {
	public enum Type {
		Success,
		Progress,
		Failure
	}
	private Type type;
	private Double progress;

	public Type type(){
		return type;
	}

	public double progress(){
		return progress;
	}

	public static PreloaderEvent Success(){
		PreloaderEvent event = new PreloaderEvent();
		event.type = Type.Success;
		return event;
	}

	public static PreloaderEvent Progress(double progress){
		PreloaderEvent event = new PreloaderEvent();
		event.type = Type.Progress;
		event.progress = progress;
		return event;
	}

	public static PreloaderEvent Failure(){
		PreloaderEvent event = new PreloaderEvent();
		event.type = Type.Failure;
		return event;
	}
}
