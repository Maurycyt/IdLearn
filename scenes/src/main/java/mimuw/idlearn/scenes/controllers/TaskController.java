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

// this controller is added dynamically to each task scene, in contrast to each other controller
public class TaskController extends GenericController {
    private ProblemPackage pkg;
    private CodeBox codeBox;

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

    public TaskController(String taskName) {
        try {
            this.pkg = PackageManager.getProblemPackage(taskName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

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

    @FXML
    private StackPane statementStackPane;
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

    private void goBackWithSaving(CodeBox codeBox) {
        CodeData data = codeBox.getSegment().saveFormat();
        String title = pkg.getTitle();
        try {
            System.out.println("Updating user code...");
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
            PointsGiver.setSolutionSpeed(pkg.getTitle(), (long) (time * 1000), 10);
            DecimalFormat df = new DecimalFormat("####0.00");
            alert = ResourceHandler.createAlert(Alert.AlertType.INFORMATION, "You've completed the task in time: " + df.format(time), ButtonType.OK);
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
        } catch (Exception | Error e) {
					e.printStackTrace();
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
        statementText.wrappingWidthProperty().bind(new ObservableValue<Double>() {
            @Override
            public void addListener(InvalidationListener invalidationListener) {
                statementScrollPane.widthProperty().addListener(invalidationListener);
            }
            @Override
            public void removeListener(InvalidationListener invalidationListener) {
                statementScrollPane.widthProperty().removeListener(invalidationListener);
            }
            @Override
            public void addListener(ChangeListener<? super Double> changeListener) {
                statementScrollPane.widthProperty().addListener((ChangeListener<? super Number>) changeListener);
            }
            @Override
            public void removeListener(ChangeListener<? super Double> changeListener) {
                statementScrollPane.widthProperty().removeListener((ChangeListener<? super Number>) changeListener);
            }
            @Override
            public Double getValue() {
                return statementScrollPane.getWidth() - 45;
            }
        });

        // this fixes the bug described here: https://bugs.openjdk.java.net/browse/JDK-8214938
        statementScrollPane.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            String target = mouseEvent.getTarget().toString();
            if (target.contains("ScrollPane") || target.contains("Text")) {
                mouseEvent.consume();
            }
        });

        // Code box
        CodeData codeData = DataManager.getUserCode(pkg.getTitle());
        codeBox = new CodeBox();
        if (codeData != null) {
            CodeSegment codeSegment = CodeSegment.recreateSegment(codeData, codeBox, dummyDragPane);
            codeBox.setSegment(codeSegment);
        }

        codeBoxScrollPane.setContent(codeBox);
        codeBox.getStyleClass().add("codeBox");

        // Submit button
        submitBtn.setOnAction(event -> submitSolution(codeBox));

        // Back button
        backBtn.setOnAction(event -> goBackWithSaving(codeBox));

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
