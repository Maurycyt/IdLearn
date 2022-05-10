package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import mimuw.idlearn.userdata.DataManager;

import java.net.URL;;
import java.util.ResourceBundle;

public class TaskSelectionController extends GenericController implements Initializable {
    @FXML
    private VBox tasksVBox;

    /** Loads all tasks from the `PackageManager`'s array as clickable buttons**/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var packages = DataManager.getUnlockedTasks();
        var btnWidth = tasksVBox.getMaxWidth();

        for (int i = 0; i <  packages.size(); i++) {
            Button btn = new Button("Task " + i + ": " + packages.get(i));
            final int finalI = i;
            btn.setOnMouseClicked((event) -> System.out.println("clicked on task #" + finalI));
            btn.setMaxWidth(btnWidth);
            tasksVBox.getChildren().add(btn);
        }
    }
}



