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
                .sorted()
                .map(AchievementManager::get)
                .forEach(a -> {
                    BorderPane achievementPane = ResourceHandler.createAchievementBorderPane(a.getDisplayedText());
                    achievementsVBox.getChildren().add(achievementPane);

                    var label = (Label)achievementPane.getLeft();
                    var pBar = (ProgressBar)achievementPane.getRight();

                    double progress = ((double)a.getProgress()) / a.getNextThreshold();

                    pBar.setProgress(progress);
                    System.out.println("Progress: " + progress + " for achievement " + a.getName());

                    if (a.getProgress() == a.getMaxProgress())
                        ResourceHandler.setStyleForFullyUnlockedAchievement(achievementPane);

                    ResourceHandler.setStyleForAchievement(label);
                });
    }
}
