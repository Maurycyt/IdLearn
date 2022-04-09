package mimuw.idlearn;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import mimuw.idlearn.core.StateMachine;
import mimuw.idlearn.problems.PackageManager;
import mimuw.idlearn.problems.ProblemPackage;

/**
 * Main class of our application
 */
public class Application extends javafx.application.Application{
	@Override
	public void start(Stage stage){
		stage.setTitle("IdLearn");

		// Load in the packages (not necessarily here)
		ProblemPackage[] packages = PackageManager.getProblemPackages();
		for (ProblemPackage p : packages) {
			System.out.println(p.getTitle());
			p.prepareTest(123);
		}

		// Here add first scene
		states.add(new Scene(states));
		
		stage.setScene(new javafx.scene.Scene((Scene)states.get(), 320, 240));
		stage.show();
		
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);

		// Main loop, updates [framesPerSecond] times per second
		timeline.getKeyFrames().add(
				new KeyFrame(
						Duration.seconds(1.0 / framesPerSecond), 
						actionEvent -> {
							var scene = (Scene)states.get(); 
							stage.getScene().setRoot(scene);
							scene.update(Duration.seconds(1.0 / framesPerSecond));
						})
		);
		timeline.play();
	}
	
	public static void main(String[] args){
		launch();
	}
	
	private final int framesPerSecond = 60;
	private final StateMachine states = new StateMachine();
}
