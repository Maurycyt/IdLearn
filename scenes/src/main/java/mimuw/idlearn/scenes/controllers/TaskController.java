package mimuw.idlearn.scenes.controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import mimuw.idlearn.idlang.GUI.CodeBox;
import mimuw.idlearn.idlang.GUI.codeblocks.CodeBlockSpawner;
import mimuw.idlearn.idlang.GUI.codeblocks.blocktypes.*;
import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.exceptions.TimeoutException;
import mimuw.idlearn.packages.PackageManager;
import mimuw.idlearn.packages.ProblemPackage;
import mimuw.idlearn.scoring.PointsGiver;
import mimuw.idlearn.scoring.TestRunner;
import mimuw.idlearn.scoring.WrongAnswerException;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TaskController extends GenericController {
    private final ProblemPackage pkg;

    public TaskController(String taskName) throws FileNotFoundException {
        this.pkg = PackageManager.getProblemPackage(taskName);
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
    private Button submitBtn;
    @FXML
    private AnchorPane dummyDragPane;

    /* Adds nice styling and connects task verification */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Statement field
        statementText.setText(pkg.getStatement());
//        statementText.setText(loremGen(45));
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
        submitBtn.setOnMousePressed(event -> {
            Expression<Void> exp = codeBox.compile();
            try {
                TestRunner runner = new TestRunner(pkg, exp);
                double time = runner.aggregateTestTimes();
                System.out.println("Correct output!");
                System.out.println("Time: " + time);
                PointsGiver.setSolutionSpeed(pkg.getTitle(), (long) (time * 1000), 10);
            } catch (WrongAnswerException e) {
                System.out.println("Incorrect output!");
            } catch (TimeoutException e) {
                System.out.println("Time out!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

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
