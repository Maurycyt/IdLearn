package mimuw.idlearn.scenes.preloader;

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

	public static PreloaderEvent Fail(){
		PreloaderEvent event = new PreloaderEvent();
		event.type = Type.Fail;
		return event;
	}
}
