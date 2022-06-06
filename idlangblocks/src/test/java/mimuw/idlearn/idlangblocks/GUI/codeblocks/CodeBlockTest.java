package mimuw.idlearn.idlangblocks.GUI.codeblocks;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import mimuw.idlearn.idlangblocks.GUI.CodeBox;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.blocktypes.Assign;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CodeBlockTest {

	private static void preparePlatform() {
		try {
			Platform.startup(() -> {
			});
		} catch (IllegalStateException e) {
			// Toolkit already initialized
		}
	}

	/*@Test
	public void testMoving() {
		preparePlatform();
		VBox codeBlocks = new VBox();
		CodeBox codeBox = new CodeBox();
		Group dragged = new Group();
		CodeBlockSpawner blockSpawner = new CodeBlockSpawner(codeBox, Assign::new);
		CodeBlock block = (CodeBlock) blockSpawner.getChildren().get(0);

		codeBlocks.getChildren().add(blockSpawner);

		assertEquals(0, dragged.getChildren().size());
		assertEquals(1, blockSpawner.getChildren().size());

		block.pressMouse(0, 0);

		assertEquals(1, dragged.getChildren().size());
		assertEquals(1, blockSpawner.getChildren().size());

		block.moveMouse(100, 100);

		assertEquals(1, dragged.getChildren().size());
		assertEquals(1, blockSpawner.getChildren().size());

		block.releaseMouse();

		assertEquals(0, dragged.getChildren().size());
		assertEquals(1, blockSpawner.getChildren().size());

	}*/
}
