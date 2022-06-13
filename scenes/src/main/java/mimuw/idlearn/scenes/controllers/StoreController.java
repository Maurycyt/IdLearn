package mimuw.idlearn.scenes.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.BorderPane;
import mimuw.idlearn.scenes.ResourceHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class StoreController extends GenericController {
    @FXML
    private Button taskStoreBtn;
    @FXML
    private Button perkStoreBtn;
   /* @FXML
    private Button cosmeticsStoreBtn;*/
    @FXML
    private BorderPane mainBorderPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Button pointsBtn = ResourceHandler.createUserPointsButton();
        mainBorderPane.setTop(pointsBtn);

        taskStoreBtn.setContentDisplay(ContentDisplay.BOTTOM);
        perkStoreBtn.setContentDisplay(ContentDisplay.BOTTOM);
        //cosmeticsStoreBtn.setContentDisplay(ContentDisplay.BOTTOM);
    }
}
