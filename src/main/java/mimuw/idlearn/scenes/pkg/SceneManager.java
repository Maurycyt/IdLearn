package mimuw.idlearn.scenes.pkg;

import javafx.scene.Scene;
import mimuw.idlearn.scenes.preloader.LoadTask;

import java.io.IOException;
import java.util.Stack;

// This class follows the singleton design pattern
public class SceneManager {
    private Stack<Scene> scenes;
    private static SceneManager sm = new SceneManager();

    private SceneManager() {
        this.scenes = new Stack<>();
    }

    public static SceneManager getInstance() {
        return sm;
    }

    public void pushScene(Scene scene) {
        scenes.push(scene);
    }
    public void pushScene(Scene scene, LoadTask loadTask) throws IOException {
        pushScene(scene);
        pushScene(new Preloader(this, loadTask));
    }

    public void replaceScene(Scene scene) {
        popScene();
        pushScene(scene);
    }
    public void replaceScene(Scene scene, LoadTask loadTask) throws IOException {
        replaceScene(scene);
        pushScene(new Preloader(this, loadTask));
    }

    public Scene popScene() {
        return scenes.pop();
    }
    public Scene peekScene() {
        return scenes.peek();
    }

}
