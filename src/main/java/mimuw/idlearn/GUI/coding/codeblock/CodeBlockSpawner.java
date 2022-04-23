package mimuw.idlearn.GUI.coding.codeblock;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import mimuw.idlearn.GUI.coding.CodeBox;

import java.util.function.Supplier;

public class CodeBlockSpawner extends AnchorPane {

	private final CodeBox codeBox;
	private final Group dragged;
	private final Supplier<CodeBlock> spawn;

	/**
	 * Spawns our CodeBlock
	 */
	public void spawnBlock() {
		CodeBlock block = spawn.get();
		block.makeDraggable(codeBox, dragged);

		this.getChildren().add(block);
	}

	/**
	 * Creates a new CodeBlockSpawner
	 *
	 * @param codeBox The box for code our CodeBlocks will interact with
	 * @param dragged The parent for nodes being dragged
	 * @param spawn   The function for spawning new CodeBlocks
	 */
	public CodeBlockSpawner(CodeBox codeBox, Group dragged, Supplier<CodeBlock> spawn) {
		super();
		this.codeBox = codeBox;
		this.dragged = dragged;
		this.spawn = spawn;

		spawnBlock();


		// Listens if the child is being moved
		// Non-moving interactions consume the event so this won't be triggered
		this.addEventHandler(
				MouseEvent.MOUSE_PRESSED,
				mouseEvent -> spawnBlock()
		);
	}

}
