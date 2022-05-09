package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import mimuw.idlearn.packages.PackageManager;
import mimuw.idlearn.packages.ProblemPackage;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class TaskSelectionController extends GenericController implements Initializable {
    @FXML
    private VBox tasksVBox;

    /** Loads all tasks from the `PackageManager`'s array as clickable buttons**/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Map<String, ProblemPackage> packages = PackageManager.getProblemPackages();
        var btnWidth = tasksVBox.getMaxWidth();
        final int NUM_BUTTONS = 30; // packages.length

        for (int i = 0; i < NUM_BUTTONS; i++) {
            Button btn = new Button("Task #" + i + ": <title>");
            int finalI = i;
            btn.setOnMouseClicked((event) -> System.out.println("clicked on task #" + finalI));
            btn.setMaxWidth(btnWidth);
            tasksVBox.getChildren().add(btn);
        }
    }
}



