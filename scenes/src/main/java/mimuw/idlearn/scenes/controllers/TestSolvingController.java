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
import mimuw.idlearn.scenes.ResourceHandler;
import mimuw.idlearn.userdata.DataManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.Scanner;

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

        inputText.wrappingWidthProperty().bind(ResourceHandler.createBinding(outputScrollPane, false, 10));

        outputTextArea.setFocusTraversable(false);
        outputTextArea.prefWidthProperty().bind(ResourceHandler.createBinding(outputScrollPane, false, 10));
        outputTextArea.prefHeightProperty().bind(ResourceHandler.createBinding(outputScrollPane, true, 10));

        Scanner scanner = null;
        try {
            int id = DataManager.getTestID(pkg.getTitle());
            DataManager.incrementTestID(pkg.getTitle());
            pkg.prepareTest(id);

            //Maurycy, work your magic here:
            scanner = pkg.getTestInputScanner(id);
            //inputText.setText(...);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
