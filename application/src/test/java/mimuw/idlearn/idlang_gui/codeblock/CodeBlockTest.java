package mimuw.idlearn.idlang_gui.codeblock;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import mimuw.idlearn.idlang_gui.CodeBox;
import mimuw.idlearn.idlang_gui.codeblock.CodeBlock;
import mimuw.idlearn.idlang_gui.codeblock.CodeBlockSpawner;
import mimuw.idlearn.idlang_gui.codeblock.blocktypes.Assign;
import org.junit.jupiter.api.BeforeAll;
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

	}
}
