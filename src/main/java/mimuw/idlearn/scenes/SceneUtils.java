package mimuw.idlearn.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

/**
 * A set of utility functions related to scenes
 */
public class SceneUtils {
	/**
	 * Load a scene from .fxml and return it's root
	 * @param name name of file
	 * @return root of loaded scene
	 * @throws IOException thrown when loading of the file fails
	 */
	public static Parent loadScene(String name) throws IOException{
		Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(SceneUtils.class.getResource(name + ".fxml"))));
		Parent root = scene.getRoot();
		scene.setRoot(new Group());
		return root;
	}
}
