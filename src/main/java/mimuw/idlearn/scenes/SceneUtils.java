package mimuw.idlearn.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

public class SceneUtils {
	public static SceneRoot loadScene(String name) throws IOException{
		Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(SceneUtils.class.getResource(name + ".fxml"))));
		SceneRoot root = (SceneRoot)scene.getRoot();
		scene.setRoot(new Group());
		return root;
	}
}
