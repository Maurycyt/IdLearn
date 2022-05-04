package mimuw.idlearn.idlang.GUI.codeblocks;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import mimuw.idlearn.idlang.GUI.CodeBox;
import mimuw.idlearn.idlang.GUI.codeblocks.CodeBlock;
import mimuw.idlearn.idlang.GUI.codeblocks.CodeBlockSpawner;
import mimuw.idlearn.idlang.GUI.codeblocks.blocktypes.Assign;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodeBlockTest {

	private static void preparePlatform() {
		try {
			Platform.startup(() -> {
			});
		} catch (IllegalStateException e) {
			// Toolkit already initialized
		}
	}

	@Test
	public void testMoving() {
		preparePlatform();
		VBox codeBlocks = new VBox();
		CodeBox codeBox = new CodeBox();
		Group dragged = new Group();
		CodeBlockSpawner blockSpawner = new CodeBlockSpawner(codeBox, dragged, Assign::new);
		CodeBlock block = (CodeBlock) blockSpawner.getChildren().get(0);

		codeBlocks.getChildren().add(blockSpawner);

		Assertions.assertEquals(0, dragged.getChildren().size());
		assertEquals(1, blockSpawner.getChildren().size());

		block.pressMouse(0, 0);

		Assertions.assertEquals(1, dragged.getChildren().size());
		assertEquals(1, blockSpawner.getChildren().size());

		block.moveMouse(100, 100);

		Assertions.assertEquals(1, dragged.getChildren().size());
		assertEquals(1, blockSpawner.getChildren().size());

		block.releaseMouse();

		Assertions.assertEquals(0, dragged.getChildren().size());
		assertEquals(1, blockSpawner.getChildren().size());

	}
}