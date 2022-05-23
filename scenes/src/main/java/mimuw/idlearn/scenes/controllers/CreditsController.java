package mimuw.idlearn.scenes.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CreditsController extends GenericController {
    @FXML
    private Text authorsText;
    @FXML
    private Text resourcesText;


    private final String authorsString = """
            Antoni Puch
            Maurycy Wojda
            Kacper Chętkowski
            Kacper Kramarz-Fernandez
            """;
    private final String resourcesString = """
            Fonts (SIL OFL):
            - Anonymous Pro: Mark Simonson (http://www.ms-studio.com)
            Icons (CC BY 3.0):
            - prank glasses: Caro Asercion (https://game-icons.net/)
            - RAM glasses, light bulb: Lorc (https://lorcblog.blogspot.com/)
            """;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        authorsText.setText(authorsString);
        resourcesText.setText(resourcesString);
    }
}
