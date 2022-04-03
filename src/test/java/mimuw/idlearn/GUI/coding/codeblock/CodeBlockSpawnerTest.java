package mimuw.idlearn.GUI.coding.codeblock;

import javafx.application.Platform;
import javafx.scene.Group;
import mimuw.idlearn.GUI.coding.CodeBox;
import mimuw.idlearn.GUI.coding.codeblock.blocktypes.Assign;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodeBlockSpawnerTest {

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
    public void spawningTest() {
        CodeBox codeBox = new CodeBox();
        Group dragged = new Group();
        CodeBlockSpawner spawner = new CodeBlockSpawner(codeBox, dragged, Assign::new);

        assertEquals(1, spawner.getChildren().size());

        spawner.spawnBlock();

        assertEquals(2, spawner.getChildren().size());

    }
}
