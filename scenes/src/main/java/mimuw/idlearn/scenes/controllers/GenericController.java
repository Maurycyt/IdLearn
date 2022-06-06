package mimuw.idlearn.scenes.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.WindowEvent;
import mimuw.idlearn.scenes.SceneManager;
import mimuw.idlearn.scenes.ResourceHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GenericController implements Initializable {
	protected final SceneManager sm = SceneManager.getInstance();

	protected void enterNextScene(URL url) throws IOException {
		sm.push(ResourceHandler.loadScene(url));
	}
	protected void enterNextScene(URL url, GenericController controller) throws IOException {
		sm.push(ResourceHandler.loadScene(url, controller));
	}
	protected void returnToPreviousScene() {
		sm.pop();
	}

	@FXML
	public void addAchievementsScene(ActionEvent event) throws IOException {
		enterNextScene(ResourceHandler.Achievements);
	}
	@FXML
	public void addGameMenuScene(ActionEvent event) throws IOException {
		enterNextScene(ResourceHandler.GameMenu);
	}
	@FXML
	public void addSettingsScene(ActionEvent event) throws IOException {
		enterNextScene(ResourceHandler.Settings);
	}
	@FXML
	public void addCreditsScene(ActionEvent event) throws IOException {
		enterNextScene(ResourceHandler.Credits);
	}
	@FXML
	public void addStoreScene(ActionEvent event) throws IOException {
		enterNextScene(ResourceHandler.Store);
	}
	@FXML
	public void addTaskStoreScene(ActionEvent event) throws IOException {
		enterNextScene(ResourceHandler.TaskStore);
	}
	@FXML
	public void addPerkStoreScene(ActionEvent event) throws IOException {
		enterNextScene(ResourceHandler.PerkStore);
	}
	@FXML
	public void addCosmeticsStoreScene(ActionEvent event) throws IOException {
		enterNextScene(ResourceHandler.CosmeticsStore);
	}
	@FXML
	public void addTaskSelectionScene(ActionEvent event) throws IOException {
		enterNextScene(ResourceHandler.TaskSelection);
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

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {}
}