package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import mimuw.idlearn.idlang.GUI.CodeBox;
import mimuw.idlearn.idlang.GUI.codeblocks.CodeBlockSpawner;
import mimuw.idlearn.idlang.GUI.codeblocks.blocktypes.*;
import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.Value;
import mimuw.idlearn.idlang.logic.exceptions.TimeoutException;
import mimuw.idlearn.idlang.logic.keywords.Block;
import mimuw.idlearn.packages.PackageManager;
import mimuw.idlearn.packages.ProblemPackage;
import mimuw.idlearn.scoring.PointsGiver;
import mimuw.idlearn.scoring.TestRunner;
import mimuw.idlearn.scoring.WrongAnswerException;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class TaskSceneController extends GenericController implements Initializable {
    private final ProblemPackage pkg;

    public TaskSceneController(String taskName) throws FileNotFoundException {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statementText.setText(pkg.getStatement());
        statementText.wrappingWidthProperty().bind(statementScrollPane.widthProperty());

        pkg.prepareTest(123); //todo: think of a place to put this
        submitBtn.setOnMousePressed(event -> {
            //Expression<Void> exp = codeBox.compile();
            Expression<Void> exp = new Block(); //todo: replace with actual compilation
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

        final CodeBox codeBox = new CodeBox(); //todo: pin this somewhere. Perhaps also change the parent class of CodeBox so it's Pane instead of Group
        final Group dragged = new Group(); // todo: @Antek, te dwie rzeczy nie są nigdzie dodane. Nie wiem gdzie to ma być. Robię na Ciebie taska

        // Create spawners
        Node readSpawner = new CodeBlockSpawner(codeBox, dragged, () -> new Read(pkg));
        Node writeSpawner = new CodeBlockSpawner(codeBox, dragged, () -> new Write(pkg));
        Node assignSpawner = new CodeBlockSpawner(codeBox, dragged, Assign::new);
        Node operationSpawner = new CodeBlockSpawner(codeBox, dragged, Operation::new);
        Node whileSpawner = new CodeBlockSpawner(codeBox, dragged, WhileBlock::new);
        Node ifSpawner = new CodeBlockSpawner(codeBox, dragged, IfElse::new);

        // Link spawners
        blockSelectionHBox.getChildren().add(readSpawner);
        blockSelectionHBox.getChildren().add(writeSpawner);
        blockSelectionHBox.getChildren().add(assignSpawner);
        blockSelectionHBox.getChildren().add(operationSpawner);
        blockSelectionHBox.getChildren().add(whileSpawner);
        blockSelectionHBox.getChildren().add(ifSpawner);
    }
}
