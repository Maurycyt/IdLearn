package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import mimuw.idlearn.scenes.SceneManager;
import mimuw.idlearn.scenes.SceneUtils;
import mimuw.idlearn.userdata.DataManager;

import java.io.IOException;
import java.net.URL;;
import java.util.List;
import java.util.ResourceBundle;

public class TaskSelectionSceneController extends GenericController implements Initializable {
    @FXML
    private VBox tasksVBox;

    /** Loads all the user's available tasks as clickable buttons **/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> tasks = DataManager.getUnlockedTasks();
        double btnWidth = tasksVBox.getMaxWidth();

        for (final String title : tasks) {
            Button btn = new Button(title);
            btn.setMaxWidth(btnWidth);
            btn.setOnMouseClicked((event) -> {
                System.out.println("Selected task \"" + title + "\"");
                try {
                    FXMLLoader loader = new FXMLLoader(SceneUtils.Task);
                    loader.setController(new TaskSceneController(title));
                    sm.push(SceneUtils.loadScene(loader));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            tasksVBox.getChildren().add(btn);
        }
    }
}



