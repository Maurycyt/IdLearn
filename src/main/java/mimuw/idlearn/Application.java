package mimuw.idlearn;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;
import mimuw.idlearn.problems.PackageManager;
import mimuw.idlearn.problems.ProblemPackage;
import mimuw.idlearn.scenes.SceneManager;
import mimuw.idlearn.scenes.SimpleScene;
import mimuw.idlearn.scenes.preloader.LoadTask;

/**
 * Main class of our application
 */
public class Application extends javafx.application.Application{
	@Override
	public void start(Stage stage){
		long start = System.currentTimeMillis();
		stage.setTitle("IdLearn");

		// Here add first scene
		Button b = new Button("Hello there");
		b.setOnAction(e -> b.setText("General Kenobi"));
		scenes.add(new SimpleScene(scenes, b), new LoadTask() {
			@Override
			public void load() {
				PackageManager.reloadProblemPackages();
				ProblemPackage[] packages = PackageManager.getProblemPackages();
				for (ProblemPackage p : packages) {
					System.out.println(p.getTitle());
					p.build();
					//p.prepareTest(123);
				}
				int n = 1000000;
				for (int i = 0; i < n; i++) {
					System.out.println("Loading very big data: " + i);
					logProgress((double)(i) / n);
				}
				logSuccess();
				System.out.println("Success!");
			}
		});

		stage.setScene(new javafx.scene.Scene(scenes.get(), 320, 240));
		stage.show();

		final Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);

		// Main loop, updates [framesPerSecond] times per second
		timeline.getKeyFrames().add(
				new KeyFrame(
						Duration.seconds(1.0 / framesPerSecond), 
						actionEvent -> {
							var scene = scenes.get();
							stage.getScene().setRoot(scene);
							scene.update(Duration.seconds(1.0 / framesPerSecond));
						})
		);

		long end = System.currentTimeMillis();
		System.out.println("Loaded app in " + (end - start) + " ms.");

		timeline.play();
	}
	
	public static void main(String[] args) {
		launch();
	}
	
	private final int framesPerSecond = 60;
	private final SceneManager scenes = new SceneManager();
}
