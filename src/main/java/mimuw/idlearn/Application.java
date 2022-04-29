package mimuw.idlearn;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import mimuw.idlearn.scenes.SceneManager;
import mimuw.idlearn.scenes.SceneRoot;
import mimuw.idlearn.scenes.SceneUtils;
import mimuw.idlearn.scenes.preloader.LoadTask;

import java.io.IOException;

public class Application extends javafx.application.Application {
	private final int framesPerSecond = 60;
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
		Image icon = new Image(getClass().getResource("icon.png").toString());
		stage.getIcons().add(icon);

		stage.setScene(new Scene(new SceneRoot()));

		// Add the main menu scene with preloading
		sceneManager.add(SceneUtils.loadScene("Application"), new LoadTask() {
			@Override
			public void load() {
				for(int i = 0; i < 100000; i++){
					System.out.println("Loading big data " + i);
					logProgress(i / 1000000.0);
				}
				logSuccess();
			}
		});

		stage.show();

		final Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);

		// Main loop, updates [framesPerSecond] times per second
		timeline.getKeyFrames().add(
				new KeyFrame(
						Duration.seconds(1.0 / framesPerSecond),
						actionEvent -> {
							var scene = sceneManager.get();
							stage.getScene().setRoot(scene);
							scene.update(Duration.seconds(1.0 / framesPerSecond));
						})
		);

		timeline.play();


		long loadEnd = System.currentTimeMillis();
		System.out.println("Loaded app in " + (loadEnd - loadStart) + "ms.");
	}

	public static void main(String[] args) {
		try {
			launch(args);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
