package mimuw.idlearn.scenes.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
                    var achievement = ResourceHandler.createAchievementHBox(a.getDisplayedText());
                    achievementsVBox.getChildren().add(achievement);

                    // make the style change dynamically
                    AchievementManager.emitter.connect(event -> {
                        if(event.type() == AchievementProgressEvent.class) {
                            AchievementProgressEvent achievementProgressEvent = (AchievementProgressEvent) event.value();
//                            if (achievementProgressEvent.name().equals(achievement.getName()) && achievementProgressEvent.level() > 0)
//                                ResourceHandler.setStyleForUnlockedAsset(achievement);
                        }
                    });
//                    if (achievement.getUnlockedLevel() > 0)
//                        ResourceHandler.setStyleForUnlockedAsset(achievement);
                });
    }
}
