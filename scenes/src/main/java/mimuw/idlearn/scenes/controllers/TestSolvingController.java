package mimuw.idlearn.scenes.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import mimuw.idlearn.idlang.logic.exceptions.WrongAnswerException;
import mimuw.idlearn.packages.PackageManager;
import mimuw.idlearn.scenes.ResourceHandler;
import mimuw.idlearn.scoring.PointsGiver;
import mimuw.idlearn.userdata.DataManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
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

    private void getAndDisplayInput() {
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
        Alert alert;
        if (outputTextArea.getText() != null && !outputTextArea.getText().isEmpty()) {
            try {
                String outputString = outputTextArea.getText();
                Writer outputWriter = pkg.getTestOutputWriter(testID);
                outputWriter.write(outputString);
                outputWriter.flush();

                if (!pkg.checkTest(testID)) {
                    throw new WrongAnswerException();
                }
                // if didn't throw, then the user has successfully completed the task
                DataManager.addPoints(5); //TODO: add constants for the points awarded and paid here and everywhere else

                alert = ResourceHandler.createAlert(Alert.AlertType.INFORMATION, "You've provided correct output\n", ButtonType.OK);
                alert.setTitle("Success");
                alert.setHeaderText("Good job!");

                // prepare the next test
                DataManager.incrementTestID(pkg.getTitle());
                getAndDisplayInput();
            } catch (Exception e) {
                alert = ResourceHandler.createAlert(Alert.AlertType.ERROR, "Try again. Remember that practice makes perfect", ButtonType.OK);
                alert.setHeaderText("Wrong output!");
            }
        } else {
            alert = ResourceHandler.createAlert(Alert.AlertType.ERROR, "Fill out the output field and try again", ButtonType.OK);
            alert.setHeaderText("No output provided!");
        }
        alert.show();
    }

    @FXML
    private ProgressBar footerProgressBar;
    @FXML
    private Text inputText;
    @FXML
    private StackPane outputStackPane;
    @FXML
    private ScrollPane outputScrollPane;
    @FXML
    private TextArea outputTextArea;

    private static final Integer STARTTIME   = 15;

    private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME * 100);

    private Timeline timeline;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        submitBtn.setOnAction(event -> submitOutput());
        backBtn.setOnAction(event -> goBack(null));

        inputText.wrappingWidthProperty().bind(ResourceHandler.createBinding(outputScrollPane, false, 10));

        outputTextArea.setFocusTraversable(false);
        outputTextArea.prefWidthProperty().bind(ResourceHandler.createBinding(outputScrollPane, false, 10));
        outputTextArea.prefHeightProperty().bind(ResourceHandler.createBinding(outputScrollPane, true, 10));
        getAndDisplayInput();

        long offset = PointsGiver.getOffset(pkg.getTitle());
        long realSpeed = PointsGiver.getSolutionRealSpeed(pkg.getTitle());

        // Bind the progressBar progressProperty
        // to the timeSeconds property
        footerProgressBar.progressProperty().bind(timeSeconds.divide(STARTTIME * 100.0).subtract(1).multiply(-1));

        // TODO - this doesn't work, needs to be fixed
        // Also needs to reflect the actual task and not a random constant
        timeSeconds.set((STARTTIME + 1) * 100);
        timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(STARTTIME + 1), new KeyValue(timeSeconds, 0)));
        timeline.playFromStart();
    }

    @FXML
    protected void handleStartAction(ActionEvent event)
    {

    }
}
