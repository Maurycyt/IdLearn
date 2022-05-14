package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import mimuw.idlearn.scenes.SceneManager;
import mimuw.idlearn.scenes.SceneUtils;

import java.io.IOException;
import java.net.URL;

public class GenericController {
	protected final SceneManager sm = SceneManager.getInstance();

	protected void replaceCurrentScene(URL url) throws IOException {
		sm.replace(SceneUtils.loadScene(url));
	}
	protected void enterNextScene(URL url) throws IOException {
		sm.push(SceneUtils.loadScene(url));
	}
	protected void enterNextScene(URL url, GenericController controller) throws IOException {
		sm.push(SceneUtils.loadScene(url, controller));
	}
	protected void returnToPreviousScene() {
		sm.pop();
	}

	@FXML
	public void addAchievementsScene(ActionEvent event) throws IOException {
		enterNextScene(SceneUtils.Achievements);
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