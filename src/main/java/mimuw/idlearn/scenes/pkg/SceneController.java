package mimuw.idlearn.scenes.pkg;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private SceneManager sm = SceneManager.getInstance();
    
    private Stage getStage(ActionEvent event) {
        return (Stage)((Node)event.getSource()).getScene().getWindow();
    }
    
    private Scene getScene(String sceneName) throws IOException {
        return new Scene(FXMLLoader.load(getClass().getResource(sceneName + ".fxml")));
    }

    private void switchToScene(String sceneName, ActionEvent event) throws IOException {
        Scene scene = getScene("MainMenu");
        sm.pushScene(scene);

        Stage stage = getStage(event);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void loadMainMenuScene(ActionEvent event) throws IOException {
        switchToScene("MainMenu", event);
    }
    @FXML
    public void loadGameMenuScene(ActionEvent event) throws IOException {
        switchToScene("GameMenu", event);
    }
    @FXML
    public void loadSettingsScene(ActionEvent event) throws IOException {
        switchToScene("Settings", event);
    }
    @FXML
    public void loadTaskSelectionScene(ActionEvent event) throws IOException {
        switchToScene("TaskSelection", event);
    }
    @FXML
    public void loadStoreScene(ActionEvent event) throws IOException {
        switchToScene("Store", event);
    }

    @FXML
    public void exitGame(ActionEvent event) throws IOException {
        //todo...
    }
}