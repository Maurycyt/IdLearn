package mimuw.idlearn.GUI.coding;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mimuw.idlearn.GUI.coding.codeblock.Assign;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlockSpawner;

public class SampleApp extends Application {


    public static void main(final String[] args) {
        launch(args);
    }




    @Override
    public void start(final Stage stage) {

        final Group root = new Group();
        final Pane codeBox = new VBox();
        final Pane codeBlocks = new VBox();
        final Group dragged = new Group();

        codeBlocks.setTranslateX(50);
        codeBlocks.setTranslateY(100);

        codeBox.setTranslateX(300);
        codeBox.setTranslateY(100);

        codeBox.setPrefSize(300, 400);


        Node assignSpawner = new CodeBlockSpawner(codeBox, dragged, Assign::new);

        codeBlocks.getChildren().add(assignSpawner);

        root.getChildren().add(codeBlocks);
        root.getChildren().add(codeBox);
        root.getChildren().add(dragged);

        final Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();

    }
}
