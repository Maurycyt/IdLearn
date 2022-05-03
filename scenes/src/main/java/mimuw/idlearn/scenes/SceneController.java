package mimuw.idlearn.scenes;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SceneController {
	private final SceneManager sm = SceneManager.getInstance();

	private void replaceCurrentScene(URL url) throws IOException {
		sm.replace(SceneUtils.loadScene(url));
	}
	private void enterNextScene(URL url) throws IOException {
		sm.push(SceneUtils.loadScene(url));
	}
	private void returnToPreviousScene() {
		sm.pop();
	}

	@FXML
	public void addAchievementsScene(ActionEvent event) throws IOException {
		enterNextScene(SceneUtils.Achievements);
	}
/*	@FXML
	public void addAdditionTaskScene(ActionEvent event) throws IOException {
		add(SceneUtils.AdditionTask);
	}*/
	@FXML
	public void addGameMenuScene(ActionEvent event) throws IOException {
		enterNextScene(SceneUtils.GameMenu);
	}
	@FXML
	public void addPreloaderScene(ActionEvent event) throws IOException {
		enterNextScene(SceneUtils.Preloader);
	}
	@FXML
	public void addSettingsScene(ActionEvent event) throws IOException {
		enterNextScene(SceneUtils.Settings);
	}
	@FXML
	public void addStoreScene(ActionEvent event) throws IOException {
		enterNextScene(SceneUtils.Store);
	}
	@FXML
	public void addTaskSelectionScene(ActionEvent event) throws IOException {
		enterNextScene(SceneUtils.TaskSelection);
	}

	@FXML
	public void goBack(ActionEvent event) {
		returnToPreviousScene();
	}

	@FXML
	public void exitGame(ActionEvent event) {
		System.out.println("Exiting game...");
		sm.exit();
	}
}