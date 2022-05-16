package mimuw.idlearn.idlang.GUI.codeblocks.blocktypes;

import javafx.scene.paint.Color;
import mimuw.idlearn.idlang.GUI.codeblocks.BlockBase;
import mimuw.idlearn.idlang.GUI.codeblocks.CodeBlock;

public class Ghost extends CodeBlock {
	/**
	 * Create a new ghost CodeBlock
	 *
	 * @param height The wanted height
	 */
	public Ghost(double height) {
		super();

		BlockBase base = new BlockBase(height, Color.TRANSPARENT); //todo: make this have an only partial transparency and inherit color from the corresponding CodeBlock
		this.getChildren().add(base);
	}

	/**
	 * Return the height of this CodeBlock
	 *
	 * @return Height
	 */
	@Override
	public double getEffectiveHeight() {
		// 0 - Because we don't want GhostBlocks to affect placement
		return 0;
	}
}
