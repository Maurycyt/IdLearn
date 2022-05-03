package mimuw.idlearn.idlangGui.codeblock.blocktypes;

import javafx.scene.paint.Color;
import mimuw.idlearn.idlangGui.codeblock.BlockBase;
import mimuw.idlearn.idlangGui.codeblock.CodeBlock;

public class End extends CodeBlock {
	private final BlockBase base;

	/**
	 * Create a new End CodeBlock
	 *
	 * @param colour Background colour
	 */
	public End(Color colour) {
		super();

		base = new BlockBase(HEIGHT, colour);

		this.getChildren().add(base);
	}
}
