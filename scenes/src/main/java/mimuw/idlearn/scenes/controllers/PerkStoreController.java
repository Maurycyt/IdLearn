package mimuw.idlearn.scenes.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mimuw.idlearn.scenes.ResourceHandler;
import mimuw.idlearn.userdata.NotEnoughPointsException;
import mimuw.idlearn.userdata.PerkManager;
import mimuw.idlearn.userdata.ReachedMaxLevelException;

import java.io.IOException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.ResourceBundle;
import java.util.Set;

public class PerkStoreController extends GenericController {
    @FXML
    private VBox perksVBox;
    @FXML
    private BorderPane mainBorderPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Button pointsBtn = ResourceHandler.createUserPointsButton();
        mainBorderPane.setTop(pointsBtn);

        Set<String> perks = PerkManager.getPerkNames();
        double btnWidth = perksVBox.getMaxWidth();
        for (String perkName : perks) {
            Button perkBtn = ResourceHandler.createGreenButton(
                    perkName + " (" + PerkManager.getLevel(perkName) + "/" + PerkManager.getMaxLevel(perkName) + ")",
                    btnWidth
            );
            perksVBox.getChildren().add(perkBtn);
            perkBtn.setOnAction((event) -> buyPerk(perkName));

            // make the style and text change dynamically
            PerkManager.connectToPerkUnlocking(event -> {
                var perkUpgrade = (AbstractMap.SimpleEntry<String, Integer>) event.value();
                if (perkName.equals(perkUpgrade.getKey())) {
                    ResourceHandler.setStyleForUnlockedAsset(perkBtn);
                    String newText = perkName + " (" + perkUpgrade.getValue() + "/" + PerkManager.getMaxLevel(perkName) + ")";
                    perkBtn.setText(newText);
                }
            });
            if (PerkManager.getLevel(perkName) > 0)
                ResourceHandler.setStyleForUnlockedAsset(perkBtn);
        }
    }

    private void buyPerk(String title) {
        Alert alert;
        try {
            PerkManager.upgradePerk(title);

            alert = ResourceHandler.createAlert(Alert.AlertType.INFORMATION,
                            "Upgraded perk \"" + title + "\"", ButtonType.OK
            );
            alert.setHeaderText("Success!");
        } catch (ReachedMaxLevelException e) {
            alert = ResourceHandler.createAlert(Alert.AlertType.WARNING,
                    "This perk can't be upgraded anymore!", ButtonType.OK
            );
            alert.setHeaderText("Max perk level achieved!");
        } catch (NotEnoughPointsException e) {
            alert = ResourceHandler.createAlert(Alert.AlertType.WARNING,
                            "Gather more points and try again! This perk costs " + e.triedToPay, ButtonType.OK
            );
            alert.setHeaderText("Not enough points!");
        } catch (IOException e) {
                e.printStackTrace();
                return;
        }
        alert.show();
    }
}
