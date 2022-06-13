package mimuw.idlearn.scenes.controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.MouseEvent;
import mimuw.idlearn.packages.PackageManager;
import mimuw.idlearn.packages.ProblemPackage;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class TestSolvingController extends TaskController {

    public TestSolvingController(String taskName) {
        try {
            this.pkg = PackageManager.getProblemPackage(taskName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        submitBtn.setOnAction(event ->  goBack(null));
        backBtn.setOnAction(event -> goBack(null));
    }
}
