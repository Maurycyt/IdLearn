package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
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
    private final static String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

    private final ProblemPackage pkg;

    public TaskSceneController(String taskName) throws FileNotFoundException {
        this.pkg = PackageManager.getProblemPackage(taskName);
    }

    private String loremGen(int n) {
        return loremIpsum.repeat(Math.max(0, n));
    }

    /*@FXML
    private Button blockSelector;*/
    @FXML
    private ScrollPane statementScrollPane;
    @FXML
    private Text statementText;

    @FXML
    private ScrollPane codeBoxScrollPane;

    @FXML
    private Button submitBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statementText.setText(pkg.getStatement());
        statementText.wrappingWidthProperty().bind(statementScrollPane.widthProperty());

        submitBtn.setOnMousePressed(event -> {
            //Expression<Void> exp = codeBox.compile();
            Expression<Void> exp = new Block();
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
    }
}
