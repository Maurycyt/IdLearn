package mimuw.idlearn.idlang.GUI.codeblocks.blocktypes;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.idlang.GUI.codeblocks.BlockBase;
import mimuw.idlearn.idlang.GUI.codeblocks.CodeBlock;

public class ElseHead extends CodeBlock {
	private final BlockBase base = new BlockBase(HEIGHT, Color.web("#aa6ee6",1.0));

	/**
	 * Create a new Else head
	 */
	public ElseHead() {
		super();

		final Text elseText = new Text("Else ");

		base.addChild(elseText);

		this.getChildren().add(base);
	}
}
