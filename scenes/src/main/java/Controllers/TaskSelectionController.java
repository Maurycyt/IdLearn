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

    /** Loads all the user's available tasks as clickable buttons **/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var tasks = DataManager.getUnlockedTasks();
        var btnWidth = tasksVBox.getMaxWidth();

        for (int i = 0; i < tasks.size(); i++) {
            Button btn = new Button(tasks.get(i));
            final int finalI = i;
            btn.setOnMouseClicked((event) -> System.out.println("Selected task \"" + finalI + "\""));
            btn.setMaxWidth(btnWidth);
            tasksVBox.getChildren().add(btn);
        }
    }
}



