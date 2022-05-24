package mimuw.idlearn.scenes.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mimuw.idlearn.scenes.ResourceHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PerkStoreController extends GenericController {
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private VBox perksVBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Button pointsBtn = ResourceHandler.createUserPointsButton();
        mainBorderPane.setTop(pointsBtn);

        Button perkBtn = new Button("Memory upgrade");
        double btnWidth = perksVBox.getMaxWidth();
        perkBtn.setMaxWidth(btnWidth);
        perkBtn.getStylesheets().add(ResourceHandler.Style.toExternalForm());
        perkBtn.getStyleClass().add("greenButton"); //TODO: add a ResourceHandler method that creates such a stylish button in one line

        perksVBox.getChildren().add(perkBtn);

   /*     taskBtn.setOnAction((event) -> {
            try {
                enterNextScene(ResourceHandler.Task, new TaskController(taskTitle));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });*/

        //PerkManager.incrementMemoryPerk()
        perksVBox.getChildren().add(perkBtn);
    }
}
