package mimuw.idlearn.idlang_gui.codeblock.blocktypes;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.idlang_gui.codeblock.BlockBase;
import mimuw.idlearn.idlang_gui.codeblock.CodeBlock;

public class ElseHead extends CodeBlock {
	private final BlockBase base = new BlockBase(HEIGHT, Color.PURPLE);

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
