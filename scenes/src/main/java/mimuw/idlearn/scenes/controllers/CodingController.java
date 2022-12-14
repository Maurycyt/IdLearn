package mimuw.idlearn.scenes.controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;
import mimuw.idlearn.idlangblocks.GUI.CodeBox;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.CodeBlockSpawner;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.CodeSegment;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.blocktypes.*;
import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.exceptions.*;
import mimuw.idlearn.packages.PackageManager;
import mimuw.idlearn.packages.ProblemPackage;
import mimuw.idlearn.scenes.ResourceHandler;
import mimuw.idlearn.scoring.PointsGiver;
import mimuw.idlearn.scoring.TestRunner;
import mimuw.idlearn.idlang.logic.exceptions.WrongAnswerException;
import mimuw.idlearn.userdata.CodeData;
import mimuw.idlearn.userdata.DataManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

public class CodingController extends TaskController {
    private CodeBox codeBox;

    public CodingController(String taskName) {
        try {
            this.pkg = PackageManager.getProblemPackage(taskName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private final EventHandler<WindowEvent> exitHandler = (event) -> {
        CodeData data = codeBox.getSegment().saveFormat();
        String title = pkg.getTitle();
        try {
            System.out.println("Saving user code (" + pkg.getTitle() + ") on exit...");
            DataManager.updateUserCode(title, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    /**
     * Makes the code for this task session save when exiting the app.
     */
    public void setSavingOnExit() {
        Scene scene = statementStackPane.getScene(); // this element is not special, could be any other
        assert scene != null;
        scene.getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, exitHandler);
    }

    /**
     * Turns off saving when exiting this controller's scene.
     */
    public void removeSavingOnExit() {
        Scene scene = statementStackPane.getScene(); // this element is not special, could be any other
        assert scene != null;
        scene.getWindow().removeEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, exitHandler);
    }


    private void goBackWithSaving(CodeBox codeBox) {
        CodeData data = codeBox.getSegment().saveFormat();
        String title = pkg.getTitle();
        try {
            System.out.println("Saving user code (" + pkg.getTitle() + ")");
            DataManager.updateUserCode(title, data);
            removeSavingOnExit();
            goBack(null);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = ResourceHandler.createAlert(Alert.AlertType.ERROR,
                    "Do you wish to go back? Your progress will not be saved",
                    ButtonType.YES, ButtonType.NO
            );
            alert.setHeaderText("Unable to save code");

            alert.showAndWait()
                    .filter(response -> response == ButtonType.YES)
                    .ifPresent(response -> goBack(null));
        }
    }

    private void submitSolution(CodeBox codeBox) {
        Expression exp = codeBox.compile();
        Alert alert = null;

        try {
            TestRunner runner = new TestRunner(pkg, exp);

            double time = runner.aggregateTestTimes();
            // if didn't throw, then the user has successfully completed the task
            int pointsGiven = switch (pkg.getDifficulty()) {
                case Easy -> 5;
                case Medium -> 10;
                case Hard -> 20;
            };
            PointsGiver.setSolutionSpeed(pkg.getTitle(), (long) (time * 3000), pointsGiven);
            DecimalFormat df = new DecimalFormat("####0.00");
            alert = ResourceHandler.createAlert(Alert.AlertType.INFORMATION, "You've completed the task in time:\n" + df.format(time), ButtonType.OK);
            alert.setTitle("Success");
            alert.setHeaderText("Good job!");
        } catch (WrongAnswerException e) {
            alert = ResourceHandler.createAlert(Alert.AlertType.ERROR, "Try again. Remember that practice makes perfect", ButtonType.OK);
            alert.setHeaderText("Wrong output!");
        } catch (TimeoutException e) {
            alert = ResourceHandler.createAlert(Alert.AlertType.ERROR, "Think about how your solution's speed can be improved", ButtonType.OK);
            alert.setHeaderText("Time out!");
        } catch (UndefinedVariableException e) {
            alert = ResourceHandler.createAlert(Alert.AlertType.ERROR, "Check your code for usages of: " + e.getVarName(), ButtonType.OK);
            alert.setHeaderText("Attempting to use an undefined variable!");
        } catch (EndOfInputException e) {
            alert = ResourceHandler.createAlert(Alert.AlertType.ERROR, "Reduce your number of Read blocks to match the input's size", ButtonType.OK);
            alert.setHeaderText("Trying to read too many variables!");
        } catch (MemoryException e) {
            alert = ResourceHandler.createAlert(Alert.AlertType.ERROR, "Think of how you can make your program more memory efficient", ButtonType.OK);
            alert.setHeaderText("Ran out of memory!");
        } catch (BadTypeException e) {
            String tmp = e.toString();
            alert = ResourceHandler.createAlert(Alert.AlertType.ERROR, tmp.substring(2 + tmp.indexOf(":")), ButtonType.OK);
            alert.setHeaderText("Type mismatch!");
        } catch (OutOfBoundsException e) {
            String tmp = e.toString();
            alert = ResourceHandler.createAlert(Alert.AlertType.ERROR, tmp.substring(2 + tmp.indexOf(":")), ButtonType.OK);
            alert.setHeaderText("Out of array bounds!");
        } catch (Exception e) {
            String text = "A";
            if (e instanceof IOException) {
                text += "n internal I/O";
                // the two cases below should never occur
            } else if (e instanceof SimulationException) {
                assert false;
                text += " compilation";
            } else {
                assert false;
                text += "n unexpected";
            }
            text += " error occurred!";
            alert = ResourceHandler.createAlert(Alert.AlertType.ERROR, "Contact your local IdLearn developer for help", ButtonType.OK);
            alert.setHeaderText(text);
            e.printStackTrace();
        } finally {
            assert alert != null;
            alert.show();
        }
    }

    @FXML
    private StackPane statementStackPane;
    @FXML
    private HBox blockSelectionHBox;
    @FXML
    private ScrollPane codeBoxScrollPane;
    @FXML
    private AnchorPane dummyDragPane;

    /* Adds nice styling and connects user solution verification */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        submitBtn.setOnAction(event -> submitSolution(codeBox));
        backBtn.setOnAction(event -> goBackWithSaving(codeBox));

        // Code box
        CodeData codeData = DataManager.getUserCode(pkg.getTitle());
        codeBox = new CodeBox();
        if (codeData != null) {
            CodeSegment codeSegment = CodeSegment.recreateSegment(codeData, codeBox, dummyDragPane);
            codeBox.setSegment(codeSegment);
            codeBox.updateIndent();
        }
        codeBoxScrollPane.setContent(codeBox);
        codeBox.getStyleClass().add("codeBox");

        // Dummy drag pane
        dummyDragPane.setVisible(true);
        dummyDragPane.managedProperty().set(false);
        dummyDragPane.toFront();

        // Block spawners
        List<Node> availableBlocks = blockSelectionHBox.getChildren();
        availableBlocks.addAll(List.of(
                new CodeBlockSpawner(codeBox, Read::new, dummyDragPane),
                new CodeBlockSpawner(codeBox, Write::new, dummyDragPane),
                new CodeBlockSpawner(codeBox, Assign::new, dummyDragPane),
                new CodeBlockSpawner(codeBox, Operation::new, dummyDragPane),
                new CodeBlockSpawner(codeBox, IfElse::new, dummyDragPane),
                new CodeBlockSpawner(codeBox, WhileBlock::new, dummyDragPane),
                new CodeBlockSpawner(codeBox, NewArray::new, dummyDragPane),
                new CodeBlockSpawner(codeBox, Set::new, dummyDragPane),
                new CodeBlockSpawner(codeBox, Get::new, dummyDragPane)
        ));
    }
}
