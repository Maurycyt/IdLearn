package mimuw.idlearn.scenes.controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
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

    private void submitOutput() {
        if ((outputTextArea.getText() != null && !outputTextArea.getText().isEmpty())) {
            System.out.println("your input: " + outputTextArea.getText());
        } else {
            System.out.println("You have not left a comment.");
        }
    }

    @FXML
    private Text inputText;
    @FXML
    private StackPane outputStackPane;
    @FXML
    private ScrollPane outputScrollPane;
    @FXML
    private TextArea outputTextArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        submitBtn.setOnAction(event -> submitOutput());
        backBtn.setOnAction(event -> goBack(null));
        outputTextArea.setFocusTraversable(false);
        outputTextArea.maxWidthProperty().bind(outputScrollPane.widthProperty());
    }
}
