package mimuw.idlearn.scenes;

import mimuw.idlearn.scenes.controllers.GenericController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import mimuw.idlearn.scenes.preloading.LoadTask;
import mimuw.idlearn.scenes.preloading.Preloader;

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
	public static URL TaskSelection = ResourceHandler.class.getResource("scenes/TaskSelection.fxml");
	public static URL Task = ResourceHandler.class.getResource("scenes/Task.fxml");

	public static URL StyleSheet = ResourceHandler.class.getResource("style.css");
	public static URL AppIcon = ResourceHandler.class.getResource("images/icon.png");
	public static URL AppLogo = ResourceHandler.class.getResource("images/logo.png");

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

	public static Parent createPreloader(LoadTask task) {
		try {
			BorderPane root = (BorderPane)loadScene(ResourceHandler.Preloader);
			root.getChildren().add(new Preloader(root, task));
			return root;
		} catch (IOException e) {
			e.printStackTrace();
			return new Preloader(null, task);
		}
	}
}
