package mimuw.idlearn.GUI.coding.codeblock;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodeBlockTest {

    @BeforeAll
    public static void preparePlatform() {
        Platform.startup(() -> {});
    }

    @Test
    public void movingTest() {
        VBox codeBlocks = new VBox();
        CodeBlock block = new Assign();
        VBox codeBox = new VBox();
        Group dragged = new Group();
        block.makeDraggable(codeBox, dragged);

        codeBlocks.getChildren().add(block);

        assertEquals(0, dragged.getChildren().size());
        assertEquals(1, codeBlocks.getChildren().size());

        block.pressMouse(0, 0);

        assertEquals(1, dragged.getChildren().size());
        assertEquals(0, codeBlocks.getChildren().size());

        block.moveMouse(100, 100);

        assertEquals(1, dragged.getChildren().size());
        assertEquals(0, codeBlocks.getChildren().size());

        block.releaseMouse();

        assertEquals(0, dragged.getChildren().size());
        assertEquals(0, codeBlocks.getChildren().size());

    }
}
