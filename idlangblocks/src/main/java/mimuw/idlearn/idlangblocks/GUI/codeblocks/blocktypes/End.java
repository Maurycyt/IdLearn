package mimuw.idlearn.idlangblocks.GUI.codeblocks.blocktypes;

import javafx.scene.paint.Color;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.BlockBase;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.CodeBlock;

public class End extends CodeBlock {

	/**
	 * Create a new End CodeBlock
	 *
	 * @param colour Background colour
	 */
	public End(Color colour) {
		super();

		BlockBase base = new BlockBase(HEIGHT, colour);

		this.getChildren().add(base);
	}
}
