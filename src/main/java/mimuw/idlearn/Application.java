package mimuw.idlearn;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import mimuw.idlearn.core.StateMachine;

public class Application extends javafx.application.Application{
	@Override
	public void start(Stage stage){
		stage.setTitle("IdLearn");
		
		states_.add(new Scene(states_));
		
		stage.setScene(new javafx.scene.Scene((Scene)states_.get(), 320, 240));
		stage.show();
		
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(
				new KeyFrame(
						Duration.seconds(1.0 / framesPerSecond_), 
						actionEvent -> {
							var scene = (Scene)states_.get(); 
							stage.getScene().setRoot(scene);
							scene.update(Duration.seconds(1.0 / framesPerSecond_));
						})
		);
		timeline.play();
	}
	
	public static void main(String[] args){
		launch();
		System.out.println("End");
	}
	
	private final int framesPerSecond_ = 60;
	private final StateMachine states_ = new StateMachine();
}
