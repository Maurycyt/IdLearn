package mimuw.idlearn.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
	private final SceneManager sm = SceneManager.getInstance();

	private Stage getStage(ActionEvent event) {
		return (Stage)((Node)event.getSource()).getScene().getWindow();
	}

	private void switchToScene(String sceneName, ActionEvent event) throws IOException {
		sm.add(SceneUtils.loadScene(sceneName));
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