package mimuw.idlearn.scenes.pkg;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void loadInitialScene(String sceneName, Stage stage) throws IOException {
        root = FXMLLoader.load(getClass().getResource(sceneName));
        this.stage = stage;
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToScene(String sceneName, ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource(sceneName));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void loadMainMenuScene(ActionEvent event) throws IOException {
        switchToScene("MainMenu.fxml", event);
    }
    @FXML
    public void loadGameMenuScene(ActionEvent event) throws IOException {
        switchToScene("GameMenu.fxml", event);
    }
    @FXML
    public void loadSettingsScene(ActionEvent event) throws IOException {
        switchToScene("Settings.fxml", event);
    }
    @FXML
    public void loadTaskSelectionScene(ActionEvent event) throws IOException {
        switchToScene("TaskSelection.fxml", event);
    }
    @FXML
    public void loadStoreScene(ActionEvent event) throws IOException {
        switchToScene("Store.fxml", event);
    }
    @FXML
    public void exitGame(ActionEvent event) throws IOException {
        //todo...
    }
}