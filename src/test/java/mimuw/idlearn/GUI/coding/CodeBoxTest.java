package mimuw.idlearn.GUI.coding;

import javafx.application.Platform;
import javafx.scene.Group;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlockSpawner;
import mimuw.idlearn.GUI.coding.codeblock.blocktypes.Assign;
import mimuw.idlearn.GUI.coding.codeblock.blocktypes.WhileBlock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodeBoxTest {

	private static void preparePlatform() {
		try {
			Platform.startup(() -> {
			});
		} catch (IllegalStateException e) {
			// Toolkit already initialized
		}
	}

	@Test
	public void testGhost() {
		preparePlatform();
		Group root = new Group();

		Group codeBlocks = new Group();
		CodeBox codeBox = new CodeBox();
		Group dragged = new Group();
		CodeBlockSpawner blockSpawner = new CodeBlockSpawner(codeBox, dragged, Assign::new);
		CodeBlock block = (CodeBlock) blockSpawner.getChildren().get(0);

		root.getChildren().add(codeBlocks);
		root.getChildren().add(dragged);
		root.getChildren().add(codeBox);

		codeBox.setTranslateX(100);
		codeBox.setTranslateY(100);

		codeBlocks.getChildren().add(blockSpawner);

		block.pressMouse(0, 0);
		block.moveMouse(100, 100);

		assertEquals(1, codeBox.getSegment().getChildren().size());

		block.moveMouse(-100, -100);

		assertEquals(0, codeBox.getSegment().getChildren().size());

		block.moveMouse(100, 100);
		block.releaseMouse();


		assertEquals(1, codeBox.getSegment().getChildren().size());
		assertEquals(0, block.getIndent());


		CodeBlockSpawner whileSpawner = new CodeBlockSpawner(codeBox, dragged, WhileBlock::new);
		CodeBlock whileBlock = (CodeBlock) whileSpawner.getChildren().get(0);

		codeBlocks.getChildren().add(whileSpawner);

		whileBlock.pressMouse(0, 0);
		whileBlock.moveMouse(100, 100);
		whileBlock.releaseMouse();

		assertEquals(0, block.getIndent());
		assertEquals(2, codeBox.getSegment().getChildren().size());

		block.pressMouse(0, 0);
		block.moveMouse(0, -CodeBlock.HEIGHT);
		block.releaseMouse();

		assertEquals(1, block.getIndent());
		assertEquals(1, codeBox.getSegment().getChildren().size());

		whileBlock.pressMouse(0, 0);
		whileBlock.moveMouse(-100, -100);

		assertEquals(1, block.getIndent());
		assertEquals(0, codeBox.getSegment().getChildren().size());

		whileBlock.releaseMouse();
	}
}
