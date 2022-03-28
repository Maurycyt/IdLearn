package mimuw.idlearn.GUI.coding.sampleapp;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mimuw.idlearn.GUI.coding.CodeBox;
import mimuw.idlearn.GUI.coding.codeblock.blocktypes.*;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlockSpawner;

public class SampleApp extends Application {


    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) {

        // Create base elements
        final Group root = new Group();
        final CodeBox codeBox = new CodeBox();
        final Pane expressionSpawners = new VBox();
        final Group dragged = new Group();

        // Set positions
        expressionSpawners.setTranslateX(50);
        expressionSpawners.setTranslateY(100);

        codeBox.setTranslateX(300);
        codeBox.setTranslateY(100);

        codeBox.setPrefSize(300, 400);

        // Create spawners
        Node assignSpawner = new CodeBlockSpawner(codeBox, dragged, Assign::new);
        Node addSpawner = new CodeBlockSpawner(codeBox, dragged, Add::new);
        Node subSpawner = new CodeBlockSpawner(codeBox, dragged, Subtract::new);
        Node mulSpawner = new CodeBlockSpawner(codeBox, dragged, Multiply::new);
        Node whileSpawner = new CodeBlockSpawner(codeBox, dragged, While::new);
        Node endSpawner = new CodeBlockSpawner(codeBox, dragged, End::new);

        // Link spawners
        expressionSpawners.getChildren().add(assignSpawner);
        expressionSpawners.getChildren().add(addSpawner);
        expressionSpawners.getChildren().add(subSpawner);
        expressionSpawners.getChildren().add(mulSpawner);
        expressionSpawners.getChildren().add(whileSpawner);
        expressionSpawners.getChildren().add(endSpawner);

        // Link everything else
        root.getChildren().add(expressionSpawners);
        root.getChildren().add(codeBox);
        root.getChildren().add(dragged);


        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        final Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        stage.setScene(scene);
        stage.show();
    }
}
