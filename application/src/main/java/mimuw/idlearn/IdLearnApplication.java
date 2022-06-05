package mimuw.idlearn;

import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mimuw.idlearn.idlang.GUI.codeblocks.CodeBlock;
import mimuw.idlearn.packages.PackageManager;
import mimuw.idlearn.packages.ProblemPackage;
import mimuw.idlearn.scenes.ResourceHandler;
import mimuw.idlearn.scenes.SceneManager;
import mimuw.idlearn.scenes.preloading.LoadTask;
import mimuw.idlearn.scoring.PointsGiver;
import mimuw.idlearn.userdata.AchievementManager;
import mimuw.idlearn.userdata.DataManager;
import mimuw.idlearn.userdata.PerkManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class IdLearnApplication extends javafx.application.Application {
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
		Image icon = new Image(ResourceHandler.AppIcon.toExternalForm());
		stage.getIcons().add(icon);

		sceneManager.setStage(stage);
		stage.setScene(new Scene(new Group()));

		// Start userdata stuff, DataManager first.
		DataManager.init();
		PerkManager.init();
		AchievementManager.init();

		// Start points giver
		PointsGiver.loadSpeeds();

		// Add the main menu scene with preloading
		sceneManager.push(ResourceHandler.loadScene(ResourceHandler.MainMenu), new LoadTask() {
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
				ArrayList<CompletableFuture<Void>> futures = new ArrayList<>();
				long start = System.currentTimeMillis();
				AtomicInteger i = new AtomicInteger(0);
				int n = packages.size();

				System.out.println("Building packages:");
				for (ProblemPackage p : packages.values()) {
					futures.add(CompletableFuture.supplyAsync(() -> {
						System.out.println("- " + p.getTitle());
						p.build();
						logProgress(i.incrementAndGet() / (float)n);
						return null;
					}));
				}

				try {
					CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("Problem packages built in " + (System.currentTimeMillis() - start) + "ms");

				logSuccess();
			}
		});

		stage.show();

		final Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);

		long loadEnd = System.currentTimeMillis();
		System.out.println("Loaded app in " + (loadEnd - loadStart) + "ms.");
		timeline.play();

		// first available task for the user
		// DataManager.unlockTask("Addition");
	}

	public static void main(String[] args) {
		try {
			launch(args);
			// Make sure to save progress on exit.
			PointsGiver.exit();
			DataManager.exit();
			CodeBlock.exit();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
