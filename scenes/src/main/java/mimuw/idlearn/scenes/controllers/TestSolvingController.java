package mimuw.idlearn.scenes.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
				String outputString;
        if (outputTextArea.getText() == null || outputTextArea.getText().isEmpty()) {
					outputString = "";
				} else {
					outputString = outputTextArea.getText();
				}
				try {
						Writer outputWriter = pkg.getTestOutputWriter(testID);
						outputWriter.write(outputString);
						outputWriter.flush();

						if (!pkg.checkTest(testID)) {
								throw new WrongAnswerException();
						}
						// if didn't throw, then the user has successfully completed the task
						int pointsGiven = switch (pkg.getDifficulty()) {
								case Easy -> 5;
								case Medium -> 10;
								case Hard -> 20;
						};
						DataManager.addPoints(pointsGiven); //TODO: add constants for the points awarded and paid here and everywhere else

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

    private static Long solutionSpeed;

    private LongProperty timeSeconds;

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

        Long offset = PointsGiver.getOffset(pkg.getTitle());
        Long solutionSpeed = PointsGiver.getSolutionRealSpeed(pkg.getTitle());

        // Bind the progressBar progressProperty
        // to the timeSeconds property
        if (solutionSpeed == null || offset == null) {
            footerProgressBar.setProgress(0);
            return;
        }
        timeSeconds = new SimpleLongProperty(solutionSpeed / 10 - offset / 10);

        footerProgressBar.progressProperty().bind(timeSeconds.divide(solutionSpeed / 10.0).subtract(1).multiply(-1));

        // TODO - this doesn't work, needs to be fixed
        // Also needs to reflect the actual task and not a random constant
        timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(solutionSpeed - offset), new KeyValue(timeSeconds, 0)));
        EventHandler<ActionEvent> setNewAnimation = actionEvent -> {
            timeline.stop();
            timeline.getKeyFrames().clear();
            timeSeconds.set(solutionSpeed / 10);
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(solutionSpeed), new KeyValue(timeSeconds, 0)));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        };
        timeline.setOnFinished(setNewAnimation);
        timeline.play();
    }
}
