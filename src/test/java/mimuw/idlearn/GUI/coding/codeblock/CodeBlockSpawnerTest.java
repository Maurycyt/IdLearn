package mimuw.idlearn.GUI.coding.codeblock;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import mimuw.idlearn.GUI.coding.codeblock.blocktypes.Assign;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodeBlockSpawnerTest {

    @BeforeAll
    public static void preparePlatform() {
        Platform.startup(() -> {});
    }

    @Test
    public void spawningTest() {
        Pane codeBox = new VBox();
        Group dragged = new Group();
        CodeBlockSpawner spawner = new CodeBlockSpawner(codeBox, dragged, Assign::new);

        assertEquals(1, spawner.getChildren().size());

        spawner.spawnBlock();

        assertEquals(2, spawner.getChildren().size());

    }
}
