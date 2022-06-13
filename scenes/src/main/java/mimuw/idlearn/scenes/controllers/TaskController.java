package mimuw.idlearn.scenes.controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import mimuw.idlearn.packages.ProblemPackage;

import java.net.URL;
import java.util.ResourceBundle;

// this type of controller is added dynamically to each task scene, not from an .fxml file
public abstract class TaskController extends GenericController {
    protected ProblemPackage pkg;

    private void initStatementText() {
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
    }

    @FXML
    protected ScrollPane statementScrollPane;
    @FXML
    protected Text statementText;
    @FXML
    protected Button backBtn;
    @FXML
    protected Button submitBtn;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initStatementText();
    }
}
