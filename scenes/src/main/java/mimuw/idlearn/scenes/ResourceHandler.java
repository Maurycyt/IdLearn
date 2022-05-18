package mimuw.idlearn.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import mimuw.idlearn.scenes.controllers.GenericController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import mimuw.idlearn.scenes.preloading.LoadTask;
import mimuw.idlearn.scenes.preloading.Preloader;
import mimuw.idlearn.userdata.DataManager;

import java.io.IOException;
import java.net.URL;

/**
 * A set of utility functions related to scenes and other resources
 */
public class ResourceHandler {
	public static URL MainMenu = ResourceHandler.class.getResource("scenes/MainMenu.fxml");
	public static URL GameMenu = ResourceHandler.class.getResource("scenes/GameMenu.fxml");
	public static URL Preloader = ResourceHandler.class.getResource("scenes/Preloader.fxml");
	public static URL Settings = ResourceHandler.class.getResource("scenes/Settings.fxml");
	public static URL Achievements = ResourceHandler.class.getResource("scenes/Achievements.fxml");
	public static URL Store = ResourceHandler.class.getResource("scenes/Store.fxml");
	public static URL TaskStore = ResourceHandler.class.getResource("scenes/TaskStore.fxml");
	public static URL PerkStore = ResourceHandler.class.getResource("scenes/PerkStore.fxml");
	public static URL CosmeticsStore = ResourceHandler.class.getResource("scenes/CosmeticsStore.fxml");
	public static URL TaskSelection = ResourceHandler.class.getResource("scenes/TaskSelection.fxml");
	public static URL Task = ResourceHandler.class.getResource("scenes/Task.fxml");

	public static URL StyleSheet = ResourceHandler.class.getResource("style.css");
	public static URL AppIcon = ResourceHandler.class.getResource("images/icon.png");

	/**
	 * Load a scene from an .fxml file and return its root
	 * @param url url of the file
	 * @return root of loaded scene
	 * @throws IOException thrown when loading of the file fails
	 */
	public static Parent loadScene(URL url) throws IOException {
		return loadScene(url, null);
	}

	/**
	 * Same as above, but sets the scene's controller based on its 2nd argument.
	 * @param url url of the file
	 * @return root of loaded scene
	 * @throws IOException thrown when loading of the file fails
	 */
	public static Parent loadScene(URL url, GenericController controller) throws IOException {
		FXMLLoader loader = new FXMLLoader(url);
		if (controller != null) {
			loader.setController(controller);
		}
		Scene scene = new Scene(loader.load());
		Parent root = scene.getRoot();
		if (url != Task) { // The Task scene's style is handled separately
			root.getStylesheets().add(StyleSheet.toExternalForm());
		}
		scene.setRoot(new Group());
		return root;
	}

	/**
	 * Creates a preloader for the `task`
	 * @param task some time-consuming task which the user must wait for
	 * @return root of the Preloader scene
	 */
	public static Parent createPreloader(LoadTask task) throws IOException {
		BorderPane root = (BorderPane)loadScene(ResourceHandler.Preloader);
		root.getChildren().add(new Preloader(task));
		return root;
	}

	public static Button createUserPointsButton() {
		Button btn = new Button();
		btn.getStylesheets().add(StyleSheet.toExternalForm());
		btn.getStyleClass().add("unclickableButton");
		btn.setText("Points: " + DataManager.showPoints());
		DataManager.connectToPoints(event -> btn.setText("Points: " + DataManager.showPoints()));
		btn.setAlignment(Pos.CENTER);
		BorderPane.setMargin(btn, new Insets(40, 0, 0, 0));
		BorderPane.setAlignment(btn, Pos.CENTER);
		return btn;
	}

	public static void addStylesheetToAlert(Alert alert) {
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(StyleSheet.toExternalForm());
		dialogPane.getStyleClass().add("dialog-pane");

	}
}
