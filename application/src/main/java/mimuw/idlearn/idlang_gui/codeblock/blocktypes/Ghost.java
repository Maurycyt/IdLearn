package mimuw.idlearn.idlang_gui.codeblock.blocktypes;

import javafx.scene.paint.Color;
import mimuw.idlearn.idlang_gui.codeblock.BlockBase;
import mimuw.idlearn.idlang_gui.codeblock.CodeBlock;

public class Ghost extends CodeBlock {
	/**
	 * Create a new ghost CodeBlock
	 *
	 * @param height The wanted height
	 */
	public Ghost(double height) {
		super();

		BlockBase base = new BlockBase(height, Color.TRANSPARENT);
		this.getChildren().add(base);
	}

	/**
	 * Return the height of this CodeBlock
	 *
	 * @return Height
	 */
	@Override
	public double getHeight() {
		// 0 - Because we don't want GhostBlocks to affect placement
		return 0;
	}
}
