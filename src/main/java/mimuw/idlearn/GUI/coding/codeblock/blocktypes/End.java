package mimuw.idlearn.GUI.coding.codeblock.blocktypes;

import javafx.scene.paint.Color;
import mimuw.idlearn.GUI.coding.codeblock.BlockBase;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;
import mimuw.idlearn.language.base.Expression;

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
