package mimuw.idlearn.scenes.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mimuw.idlearn.packages.ProblemPackage;
import mimuw.idlearn.scenes.ResourceHandler;
import mimuw.idlearn.userdata.DataManager;
import mimuw.idlearn.userdata.NotEnoughPointsException;
import mimuw.idlearn.userdata.PerkManager;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

public class PerkStoreController extends GenericController {
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private VBox perksVBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Button pointsBtn = ResourceHandler.createUserPointsButton();
        mainBorderPane.setTop(pointsBtn);

        Set<String> titles = PerkManager.getPerkNames();
        double btnWidth = perksVBox.getWidth();
        for (String title : titles) {
            Button perkBtn = ResourceHandler.createGreenButton(title, btnWidth);
            perksVBox.getChildren().add(perkBtn);
            perkBtn.setOnAction((event) -> buyPerk(title));
        }
    }

    private void buyPerk(String title) {
        Alert alert;
        try {
            DataManager.payPoints(0); //todo: replace with actual cost

            alert = ResourceHandler.createAlert(Alert.AlertType.INFORMATION,
                    "Acquired perk \"" + title + "\"", ButtonType.OK
            );
            alert.setHeaderText("Success!");
            PerkManager.upgradePerk(title);
        } catch (NotEnoughPointsException e) {
            alert = ResourceHandler.createAlert(Alert.AlertType.WARNING,
                    "Gather more points and try again", ButtonType.OK
            );
            alert.setHeaderText("Not enough points!");
        }
        alert.show();
    }
}
