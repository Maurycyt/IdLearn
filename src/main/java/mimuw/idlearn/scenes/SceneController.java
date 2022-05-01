package mimuw.idlearn.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;

public class SceneController {
	private final SceneManager sm = SceneManager.getInstance();

	private void switchToScene(URL url) throws IOException {
		sm.replace(SceneUtils.loadScene(url));
	}

	@FXML
	public void loadMainMenuScene(ActionEvent event) throws IOException {
		switchToScene(SceneUtils.MainMenu);
	}
	@FXML
	public void loadGameMenuScene(ActionEvent event) throws IOException {
		switchToScene(SceneUtils.GameMenu);
	}
	@FXML
	public void loadSettingsScene(ActionEvent event) throws IOException {
		switchToScene(SceneUtils.Settings);
	}
	@FXML
	public void loadTaskSelectionScene(ActionEvent event) throws IOException {
		switchToScene(SceneUtils.TaskSelection);
	}
	@FXML
	public void loadStoreScene(ActionEvent event) throws IOException {
		switchToScene(SceneUtils.Store);
	}

	@FXML
	public void exitGame(ActionEvent event) {
		System.out.println("Exit game");
		sm.exit();
	}
}