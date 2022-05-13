package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import mimuw.idlearn.packages.PackageManager;
import mimuw.idlearn.packages.ProblemPackage;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class TaskSceneController extends GenericController implements Initializable {
    private final static String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

    private final ProblemPackage pkg;

    public TaskSceneController(String taskName) throws FileNotFoundException {
        this.pkg = PackageManager.getProblemPackage(taskName);
    }

    /*@FXML
    private Button blockSelector;*/
    @FXML
    private Button backBtn;
    @FXML
    private Button statementField;
    @FXML
    private Button codeField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backBtn.setOnMouseClicked(e -> goBack(null));
        statementField.setText(pkg.getStatement());
        codeField.setText(loremIpsum);
    }
}
