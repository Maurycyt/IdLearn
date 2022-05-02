package mimuw.idlearn;

import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mimuw.idlearn.problems.PackageManager;
import mimuw.idlearn.problems.ProblemPackage;
import mimuw.idlearn.scenes.SceneManager;
import mimuw.idlearn.scenes.SceneUtils;
import mimuw.idlearn.scenes.preloader.LoadTask;

import java.io.IOException;
import java.net.URL;

public class Application extends javafx.application.Application {
	private final SceneManager sceneManager = SceneManager.getInstance();

	@Override
	public void start(Stage stage) throws IOException {
		// Start measuring time of preloading
		long loadStart = System.currentTimeMillis();

		// Set the title
		stage.setTitle("IdLearn");

		// Set the stage size to the entire screen
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		stage.setWidth(screenBounds.getWidth());
		stage.setHeight(screenBounds.getHeight());

		// Set the app icon
		Image icon = new Image(SceneUtils.AppIcon.toExternalForm());
		stage.getIcons().add(icon);

		sceneManager.setStage(stage);
		stage.setScene(new Scene(new Group()));

		// Add the main menu scene with preloading
		sceneManager.add(SceneUtils.loadScene(SceneUtils.MainMenu), new LoadTask() {
			@Override
			public void load() {
				try {
					PackageManager.reloadProblemPackages();
				} catch (Exception e) {
					System.out.println("Package directory altered. Reloading packages...");
					PackageManager.reloadProblemPackageDirectory(true);
					PackageManager.reloadProblemPackages();
				}
				ProblemPackage[] packages = PackageManager.getProblemPackages();
				for (ProblemPackage p : packages) {
					System.out.println(p.getTitle());
					p.build();
				}

				for(int i = 0; i < 1000000; i++){
					//System.out.println("Loading big data " + i);
					logProgress(i / 1000000.0);
				}
				logSuccess();
			}
		});

		stage.show();

		final Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);


		long loadEnd = System.currentTimeMillis();
		System.out.println("Loaded app in " + (loadEnd - loadStart) + "ms.");
		timeline.play();
	}

	public static void main(String[] args) {
		try {
			launch(args);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
