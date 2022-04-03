package mimuw.idlearn.GUI.coding.sampleapp;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
        final Pane codeBlocks = new VBox();
        final Group dragged = new Group();

        // Set positions
        codeBlocks.setTranslateX(50);
        codeBlocks.setTranslateY(100);

        codeBox.setTranslateX(300);
        codeBox.setTranslateY(100);

        //codeBox.setPrefSize(300, 400);

        // Create spawners
        Node assignSpawner = new CodeBlockSpawner(codeBox, dragged, Assign::new);
        Node addSpawner = new CodeBlockSpawner(codeBox, dragged, Add::new);
        Node subSpawner = new CodeBlockSpawner(codeBox, dragged, Subtract::new);
        Node mulSpawner = new CodeBlockSpawner(codeBox, dragged, Multiply::new);
        Node whileSpawner = new CodeBlockSpawner(codeBox, dragged, WhileBlock::new);

        // Link spawners
        codeBlocks.getChildren().add(assignSpawner);
        codeBlocks.getChildren().add(addSpawner);
        codeBlocks.getChildren().add(subSpawner);
        codeBlocks.getChildren().add(mulSpawner);
        codeBlocks.getChildren().add(whileSpawner);

        // Link everything else
        root.getChildren().add(codeBlocks);
        root.getChildren().add(codeBox);
        root.getChildren().add(dragged);

        final Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
