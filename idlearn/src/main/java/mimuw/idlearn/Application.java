package mimuw.idlearn;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import mimuw.idlearn.core.StateMachine;
import mimuw.idlearn.problems.PackageManager;
import mimuw.idlearn.problems.ProblemPackage;
import mimuw.idlearn.scenes.Preloader;
import mimuw.idlearn.scenes.Scene;
import mimuw.idlearn.scenes.SimpleScene;

/**
 * Main class of our application
 */
public class Application extends javafx.application.Application{
	@Override
	public void start(Stage stage){
		stage.setTitle("IdLearn");

		// Here add first scene
		states.add(new Preloader(states, new Preloader.LoadTask() {
			@Override
			public void load() {
				// Load in the packages (not necessarily here)
				ProblemPackage[] packages = PackageManager.getProblemPackages();
				for (ProblemPackage p : packages) {
					System.out.println(p.getTitle());
					p.prepareTest(123);
				}

				int n = 1000000;
				for (int i = 0; i < n; i++) {
					System.out.println("Loading very big data: " + i);
					logProgress((double)(i) / n);
				}
				logSuccess();
				System.out.println("Success!");
			}
		}, new SimpleScene(states)));

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
