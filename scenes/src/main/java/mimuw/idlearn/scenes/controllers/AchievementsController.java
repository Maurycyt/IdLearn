package mimuw.idlearn.scenes.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
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

        //double width = achievementsVBox.getMaxWidth();

        AchievementManager.getAchievementNames()
                .stream()
                .map(AchievementManager::get)
                .forEach(a -> {
                    HBox achievementHBox = ResourceHandler.createAchievementHBox(a.getDisplayedText());
                    achievementsVBox.getChildren().add(achievementHBox);

                    var label = (Label)achievementHBox.getChildren().get(0);
                    var pBar = (ProgressBar)achievementHBox.getChildren().get(1);

                    // make the style change dynamically
                    AchievementManager.emitter.connect(event -> {
                        if (event.type() == AchievementProgressEvent.class) {
                            AchievementProgressEvent achievementProgressEvent = (AchievementProgressEvent) event.value();
                            // update the progress bar
                            if (achievementProgressEvent.name().equals(a.getName()) && achievementProgressEvent.level() > 0) {
                                pBar.setProgress(achievementProgressEvent.progress());
                            }
                        }
                    });
                    //TODO: tego nie musi być bo brzydko wygląda ten żółty/złoty tutaj
                    /*if (a.getUnlockedLevel() > 0)
                        ResourceHandler.setStyleForUnlockedAsset(label);*/
                });
    }
}
