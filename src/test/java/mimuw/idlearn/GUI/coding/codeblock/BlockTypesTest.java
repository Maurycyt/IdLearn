package mimuw.idlearn.GUI.coding.codeblock;

import javafx.application.Platform;
import mimuw.idlearn.GUI.coding.codeblock.blocktypes.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BlockTypesTest {
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

    // In the future more needs to be added to these tests
    // For example to see if they convert properly
    // But for now those blocks don't do much
    @Test
    public void AssignTest() {
        new Assign();
    }

    @Test
    public void AddTest() {
        new Add();
    }

    @Test
    public void SubTest() {
        new Subtract();
    }

    @Test
    public void MulTest() {
        new Multiply();
    }

    @Test
    public void WhileTest() {
        new While();
    }

    @Test
    public void EndTest() {
        new End();
    }
}
