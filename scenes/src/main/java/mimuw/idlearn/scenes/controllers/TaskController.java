package mimuw.idlearn.scenes.controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import mimuw.idlearn.idlang.GUI.CodeBox;
import mimuw.idlearn.idlang.GUI.codeblocks.CodeBlockSpawner;
import mimuw.idlearn.idlang.GUI.codeblocks.CodeSegment;
import mimuw.idlearn.idlang.GUI.codeblocks.blocktypes.*;
import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.exceptions.*;
import mimuw.idlearn.packages.PackageManager;
import mimuw.idlearn.packages.ProblemPackage;
import mimuw.idlearn.scoring.PointsGiver;
import mimuw.idlearn.scoring.TestRunner;
import mimuw.idlearn.scoring.WrongAnswerException;
import mimuw.idlearn.userdata.DataManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

// this controller is added dynamically to each task scene, in contrast to each other controller
public class TaskController extends GenericController {
    private boolean taskCompletedNow;
    private ProblemPackage pkg;

    public TaskController(String taskName) {
        try {
            this.pkg = PackageManager.getProblemPackage(taskName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // something for tests
    private final static String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    private String loremGen(int n) {
        return loremIpsum.repeat(Math.max(0, n));
    }

    @FXML
    private ScrollPane statementScrollPane;
    @FXML
    private Text statementText;
    @FXML
    private HBox blockSelectionHBox;
    @FXML
    private ScrollPane codeBoxScrollPane;
    @FXML
    private Button backBtn;
    @FXML
    private Button submitBtn;
    @FXML
    private AnchorPane dummyDragPane;

    private void questionGoingBack(CodeBox codeBox) {
        CodeSegment codeSegment = (CodeSegment) codeBox.getChildren().get(0);

        // No progress will be lost, go back like normal
        if (codeSegment.getChildren().size() == 0 || taskCompletedNow) {
            goBack(null);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to go back? Your progress will not be saved.",
                    ButtonType.YES, ButtonType.CANCEL
            );
            alert.initStyle(StageStyle.DECORATED); //TODO: how to style this?

            alert.showAndWait()
                    .filter(response -> response == ButtonType.YES)
                    .ifPresent(response -> goBack(null));
        }
    }

    private void submitSolution(CodeBox codeBox) {
        Expression<Void> exp = codeBox.compile();
        Alert alert = null;
        try {
            final boolean awardPoints = !PointsGiver.getCompletedTasks().contains(pkg.getTitle());

            TestRunner runner = new TestRunner(pkg, exp);
            double time = runner.aggregateTestTimes();
            // if didn't throw, then the user has successfully completed the task
            taskCompletedNow = true;
            if (awardPoints) {
                PointsGiver.setSolutionSpeed(pkg.getTitle(), (long) (time * 1000), 10);
            }

            alert = new Alert(Alert.AlertType.INFORMATION, "You've completed the task in time: " + time, ButtonType.OK);
            alert.setTitle("Success");
            alert.setHeaderText("Good job!");
        } catch (WrongAnswerException e) {
            alert = new Alert(Alert.AlertType.ERROR, "Remember that practice makes perfect", ButtonType.OK);
            alert.setHeaderText("Wrong output!");
        } catch (SimulationException e) {
            if (e instanceof TimeoutException) {
                alert = new Alert(Alert.AlertType.ERROR, "Think about how your solution's speed can be improved", ButtonType.OK);
                alert.setHeaderText("Time out!");
            } else if (e instanceof UndefinedVariableException) {
                alert = new Alert(Alert.AlertType.ERROR, "Check your code for usages of: " + ((UndefinedVariableException) e).getVarName(), ButtonType.OK);
                alert.setHeaderText("Attempting to use an undefined variable!");
            } else if (e instanceof EndOfInputException) {
                alert = new Alert(Alert.AlertType.ERROR, "Reduce your number of Read blocks to match the input's size", ButtonType.OK);
                alert.setHeaderText("Trying to read too many variables!");
            } else if (e instanceof MemoryException) {
                alert = new Alert(Alert.AlertType.ERROR, "Think of how you can make your program more memory efficient", ButtonType.OK);
                alert.setHeaderText("Ran out of memory!");
            }
        } catch (IOException e) {
            alert = new Alert(Alert.AlertType.ERROR, "Contact your local IdLearn developer for help", ButtonType.OK);
            alert.setHeaderText("An Internal IO Error Occurred!");
        } finally {
            assert alert != null;
            alert.show();
        }
    }

    /* Adds nice styling and connects task verification */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Statement field
        statementText.setText(pkg.getStatement());
        statementText.wrappingWidthProperty().bind(statementScrollPane.widthProperty());

        // these two lines are an attempt to fix the bug described here: https://bugs.openjdk.java.net/browse/JDK-8214938
        statementText.setOnMousePressed(Event::consume);
        statementScrollPane.setOnMousePressed(Event::consume);

        // Code box
        final CodeBox codeBox = new CodeBox();
        codeBox.setStyle(
                "-fx-border-color: #00b167;" +
                "-fx-border-width: 4px;" +
                "-fx-border-radius: 10px;" +
                "-fx-background-radius: 10px;" +
                "-fx-background-color: #f2f2f5;"
        );
        codeBoxScrollPane.setContent(codeBox);
        codeBoxScrollPane.setStyle(
                "-fx-background-color: transparent;"
        );

        // Submit button
        submitBtn.setOnAction(event -> submitSolution(codeBox));

        // Back button
        backBtn.setOnAction(event -> questionGoingBack(codeBox));

        // Dummy drag pane
        dummyDragPane.setVisible(true);
        dummyDragPane.managedProperty().set(false);
        dummyDragPane.toFront();

        // Block selection HBox
        blockSelectionHBox.setStyle(
                "-fx-alignment: center; " +
                "-fx-spacing: 45px;" +
                "-fx-background-color: #f2f2f5;"
        );

        // Block spawners
        List<Node> availableBlocks = blockSelectionHBox.getChildren();
        availableBlocks.addAll(List.of(
                new CodeBlockSpawner(codeBox, () -> new Read(pkg), dummyDragPane),
                new CodeBlockSpawner(codeBox, () -> new Write(pkg), dummyDragPane),
                new CodeBlockSpawner(codeBox, Assign::new, dummyDragPane),
                new CodeBlockSpawner(codeBox, Operation::new, dummyDragPane),
                new CodeBlockSpawner(codeBox, IfElse::new, dummyDragPane),
                new CodeBlockSpawner(codeBox, WhileBlock::new, dummyDragPane)
        ));
    }
}
