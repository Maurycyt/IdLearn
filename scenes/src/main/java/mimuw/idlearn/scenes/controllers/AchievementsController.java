package mimuw.idlearn.scenes.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mimuw.idlearn.achievements.AchievementManager;
import mimuw.idlearn.achievements.AchievementProgressEvent;
import mimuw.idlearn.scenes.ResourceHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class AchievementsController extends GenericController {
    @FXML
    private VBox achievementsVBox;

    @FXML
    private BorderPane mainBorderPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Button pointsBtn = ResourceHandler.createUserPointsButton();
        mainBorderPane.setTop(pointsBtn);

        AchievementManager.getAchievementNames()
                .stream()
                .map(AchievementManager::get)
                .forEach(a -> {
                    BorderPane achievementPane = ResourceHandler.createAchievementBorderPane(a.getDisplayedText());
                    achievementsVBox.getChildren().add(achievementPane);

                    var label = (Label)achievementPane.getLeft();
                    var pBar = (ProgressBar)achievementPane.getRight();

                    double progress = ((double)a.getUnlockedLevel()) / a.getNextThreshold();

                    pBar.setProgress(progress);
                    System.out.println("Progress: " + progress + " for achievement " + a.getName());

                    // make the style change dynamically
/*                    AchievementManager.emitter.connect(e -> {
                        if (e.type() == AchievementProgressEvent.class) {
                            AchievementProgressEvent event = (AchievementProgressEvent) e.value();
                            // update the progress bar
                            if (event.name().equals(a.getName()) && event.level() > 0) {
                                pBar.setProgress(event.progress());
                            }
                        }
                    });*/
                    /*if (a.getUnlockedLevel() > 0)
                        ResourceHandler.setStyleForUnlockedAsset(label);*/
                });
    }
}
