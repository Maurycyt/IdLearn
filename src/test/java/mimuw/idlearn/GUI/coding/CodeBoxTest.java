package mimuw.idlearn.GUI.coding;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;
import mimuw.idlearn.GUI.coding.codeblock.blocktypes.Assign;
import mimuw.idlearn.GUI.coding.codeblock.blocktypes.While;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodeBoxTest {
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
    public void ghostTest() {
        Group root = new Group();

        VBox codeBlocks = new VBox();
        CodeBlock block = new Assign();
        CodeBox codeBox = new CodeBox();
        Group dragged = new Group();
        block.makeDraggable(codeBox, dragged);

        root.getChildren().add(codeBlocks);
        root.getChildren().add(dragged);
        root.getChildren().add(codeBox);

        codeBox.setTranslateX(100);
        codeBox.setTranslateY(100);

        codeBlocks.getChildren().add(block);

        block.pressMouse(0, 0);
        block.moveMouse(100, 100);

        assertEquals(1, codeBox.getChildren().size());

        block.moveMouse(-100, -100);

        assertEquals(0, codeBox.getChildren().size());

        block.moveMouse(100, 100);
        block.releaseMouse();

        assertEquals(1, codeBox.getChildren().size());
        assertEquals(0, block.getIndent());

        CodeBlock whileBlock = new While();
        whileBlock.makeDraggable(codeBox, dragged);
        codeBlocks.getChildren().add(whileBlock);

        whileBlock.pressMouse(0, 0);
        whileBlock.moveMouse(100, 100);

        assertEquals(1, block.getIndent());
        assertEquals(2, codeBox.getChildren().size());

        whileBlock.moveMouse(-100, -100);

        assertEquals(0, block.getIndent());
        assertEquals(1, codeBox.getChildren().size());

        whileBlock.moveMouse(100, 100);
        whileBlock.releaseMouse();

        assertEquals(1, block.getIndent());
        assertEquals(2, codeBox.getChildren().size());
    }
}
