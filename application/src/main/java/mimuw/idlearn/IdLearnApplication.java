package mimuw.idlearn;

import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mimuw.idlearn.packages.PackageManager;
import mimuw.idlearn.packages.ProblemPackage;
import mimuw.idlearn.scenes.SceneManager;
import mimuw.idlearn.scenes.SceneUtils;
import mimuw.idlearn.scenes.preloader.LoadTask;
import mimuw.idlearn.scoring.PointsGiver;
import mimuw.idlearn.userdata.DataManager;

import java.io.IOException;
import java.util.Map;

public class IdLearnApplication extends javafx.application.Application {
	private final SceneManager sceneManager = SceneManager.getInstance();
	private final int framesPerSecond = 60;

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

		// Start data manager
		DataManager.init();

		// Start points giver
		PointsGiver.loadSpeeds();

		// Add the main menu scene with preloading
		sceneManager.push(SceneUtils.loadScene(SceneUtils.MainMenu), new LoadTask() {
			@Override
			public void load() {
				try {
					PackageManager.reloadProblemPackages();
				} catch (RuntimeException e) {
					System.out.println("Package directory altered. Reloading packages...");
					PackageManager.reloadProblemPackageDirectory(true);
					PackageManager.reloadProblemPackages();
				}
				Map<String, ProblemPackage> packages = PackageManager.getProblemPackages();
				int i = 0, n = packages.size();
				for (ProblemPackage p : packages.values()) {
					System.out.println("Building package " + "\"" + p.getTitle() + "\"...");
					p.build();
					logProgress(i / (float)n);
					i++;
				}

				logSuccess();
			}
		});

		stage.show();

		final Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);

		long loadEnd = System.currentTimeMillis();
		System.out.println("Loaded app in " + (loadEnd - loadStart) + "ms");
		timeline.play();
	}

	public static void main(String[] args) {
		try {
			launch(args);
			// Make sure to save progress on exit.
			PointsGiver.exit();
			DataManager.exit();
			System.out.println("Thanks for playing IdLearn, goodbye!");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
