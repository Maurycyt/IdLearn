package mimuw.idlearn.scenes;

import Controllers.GenericController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import mimuw.idlearn.scenes.preloader.LoadTask;
import mimuw.idlearn.scenes.preloader.Preloader;

import java.io.IOException;
import java.net.URL;

/**
 * A set of utility functions related to scenes
 */
public class SceneUtils {
	public static URL MainMenu = SceneUtils.class.getResource("scenes/MainMenu.fxml");
	public static URL GameMenu = SceneUtils.class.getResource("scenes/GameMenu.fxml");
	public static URL Preloader = SceneUtils.class.getResource("scenes/Preloader.fxml");
	public static URL Settings = SceneUtils.class.getResource("scenes/Settings.fxml");
	public static URL Achievements = SceneUtils.class.getResource("scenes/Achievements.fxml");
	public static URL Store = SceneUtils.class.getResource("scenes/Store.fxml");
	public static URL TaskSelection = SceneUtils.class.getResource("scenes/TaskSelection.fxml");
	public static URL Task = SceneUtils.class.getResource("scenes/Task.fxml");

	//public static URL AdditionTask = SceneUtils.class.getResource("scenes/AdditionTask.fxml");

	public static URL StyleSheet = SceneUtils.class.getResource("style.css");
	public static URL AppIcon = SceneUtils.class.getResource("images/icon.png");
	public static URL AppLogo = SceneUtils.class.getResource("images/logo.png");

	/**
	 * Load a scene from an .fxml file and return its root
	 * @param url url of the file
	 * @return root of loaded scene
	 * @throws IOException thrown when loading of the file fails
	 */
	public static Parent loadScene(URL url) throws IOException {
		return loadScene(url, null);
	}
	public static Parent loadScene(URL url, GenericController controller) throws IOException {
		FXMLLoader loader = new FXMLLoader(url);
		if (controller != null) {
			loader.setController(controller);
		}
		Scene scene = new Scene(loader.load());
		Parent root = scene.getRoot();
		root.getStylesheets().add(StyleSheet.toExternalForm());
		scene.setRoot(new Group());
		return root;
	}
	public static Parent createPreloader(LoadTask task) {
		try {
			BorderPane root = (BorderPane)loadScene(SceneUtils.Preloader);
			root.getChildren().add(new Preloader(root, task));
			return root;
		} catch (IOException e) {
			e.printStackTrace();
			return new Preloader(null, task);
		}
	}
}
