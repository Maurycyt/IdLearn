package mimuw.idlearn.idlangblocks.GUI.codeblocks.blocktypes;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.BlockBase;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.CodeBlock;

public class ElseHead extends CodeBlock {

	/**
	 * Create a new Else head
	 */
	public ElseHead() {
		super();

		final Text elseText = new Text("Else ");

		BlockBase base = new BlockBase(HEIGHT, Color.web("#aa6ee6", 1.0));
		base.addChild(elseText);

		this.getChildren().add(base);
	}
}
