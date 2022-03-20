package mimuw.idlearn.GUI.coding.codeblock;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import mimuw.idlearn.GUI.coding.CodeBox;
import mimuw.idlearn.GUI.coding.codeblock.blocktypes.Assign;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodeBlockTest {

    @BeforeAll
    public static void preparePlatform() {
        try {
            Platform.startup(() -> {
            });
        }
        catch (IllegalStateException e) {
            // Toolkit already initialized
        }
    }

    @Test
    public void movingTest() {
        VBox codeBlocks = new VBox();
        CodeBlock block = new Assign();
        CodeBox codeBox = new CodeBox();
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
