package mimuw.idlearn.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class SceneController {
	private final SceneManager sm = SceneManager.getInstance();

	private void switchToScene(String sceneName) throws IOException {
		sm.replace(SceneUtils.loadScene(sceneName));
	}

	@FXML
	public void loadMainMenuScene(ActionEvent event) throws IOException {
		switchToScene("MainMenu");
	}
	@FXML
	public void loadGameMenuScene(ActionEvent event) throws IOException {
		switchToScene("GameMenu");
	}
	@FXML
	public void loadSettingsScene(ActionEvent event) throws IOException {
		switchToScene("Settings");
	}
	@FXML
	public void loadTaskSelectionScene(ActionEvent event) throws IOException {
		switchToScene("TaskSelection");
	}
	@FXML
	public void loadStoreScene(ActionEvent event) throws IOException {
		switchToScene("Store");
	}

	@FXML
	public void exitGame(ActionEvent event) throws IOException {
		System.out.println("Exit game");
		sm.exit();
	}
}