package mimuw.idlearn.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;

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
	@FXML
	public void addAdditionTaskScene(ActionEvent event) throws IOException {
		/* Will be replaced by:
		```
			enterNextScene(SceneUtils.AdditionTask);
		```*/
		Scene scene = SceneUtils.createAdditionTaskScene();
		Parent root = scene.getRoot();
		scene.setRoot(new Group());
		sm.push(root);

	}
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