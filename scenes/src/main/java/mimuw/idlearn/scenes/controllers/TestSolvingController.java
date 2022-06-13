package mimuw.idlearn.scenes.controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import mimuw.idlearn.idlang.logic.exceptions.WrongAnswerException;
import mimuw.idlearn.packages.PackageManager;
import mimuw.idlearn.packages.ProblemPackage;
import mimuw.idlearn.scenes.ResourceHandler;
import mimuw.idlearn.userdata.DataManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.Scanner;

public class TestSolvingController extends TaskController {

		private int testID;

    public TestSolvingController(String taskName) {
        try {
            this.pkg = PackageManager.getProblemPackage(taskName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

		private void getInput() {
			try {
				testID = DataManager.getTestID(pkg.getTitle());
				pkg.prepareTest(testID);

				Scanner inputScanner = pkg.getTestInputScanner(testID);
				StringBuilder sb = new StringBuilder();
				while (inputScanner.hasNextLine()) {
					sb.append(inputScanner.nextLine());
					sb.append("\n");
				}
				inputText.setText(sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

    private void submitOutput() {
        if ((outputTextArea.getText() != null && !outputTextArea.getText().isEmpty())) {
					try {
						String outputString = outputTextArea.getText();
						Writer outputWriter = pkg.getTestOutputWriter(testID);
						outputWriter.write(outputString);
						outputWriter.flush();

						if (!pkg.checkTest(testID)) {
							throw new WrongAnswerException();
						}

						DataManager.addPoints(5);

						// Popup że ok

						DataManager.incrementTestID(pkg.getTitle());
						getInput();
					} catch (Exception e) {
						// Kacper magic
					}
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
				getInput();
    }
}
