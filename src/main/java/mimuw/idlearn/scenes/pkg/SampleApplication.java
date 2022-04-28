package mimuw.idlearn.scenes.pkg;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import mimuw.idlearn.problems.PackageManager;
import mimuw.idlearn.problems.ProblemPackage;
import mimuw.idlearn.scenes.preloader.LoadTask;

import java.io.IOException;
import java.util.Objects;

public class SampleApplication extends Application {
    private final int FRAMES_PER_SECOND = 60;
    private final SceneManager sceneManager = SceneManager.getInstance();

    @Override
    public void start(Stage stage) throws IOException {
        // Set the title
        stage.setTitle("IdLearn");

        // Set the stage size to the entire screen
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());

        // Set the app icon
        Image icon = new Image(Objects.requireNonNull(getClass().getResource("icon.png")).toString());
        stage.getIcons().add(icon);

        // Start measuring time of preloading
        long loadStart = System.currentTimeMillis();

        // Add the main menu scene with preloading
        sceneManager.pushScene(
                new Scene(FXMLLoader.load(getClass().getResource("MainMenu.fxml"))),
                new LoadTask() {
                    @Override
                    public void load() {
                        PackageManager.reloadProblemPackages();
                        ProblemPackage[] packages = PackageManager.getProblemPackages();
                        for (ProblemPackage p : packages) {
                            System.out.println(p.getTitle());
                            p.build();
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
        //todo: cośtam z rootem idk
        stage.setScene(sceneManager.peekScene());
        stage.show();

        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        //Main loop, updates [framesPerSecond] times per second
//        timeline.getKeyFrames().add(
//                new KeyFrame(
//                        Duration.seconds(1.0 / FRAMES_PER_SECOND),
//                        actionEvent -> {
//                            var scene = sceneManager.peekScene();
//                            stage.getScene().setRoot(scene);
//                            scene.update(Duration.seconds(1.0 / FRAMES_PER_SECOND));
//                        })
//        );

        long loadEnd = System.currentTimeMillis();
        System.out.println("Loaded app in " + (loadEnd - loadStart) + "ms.");

        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}