package mimuw.idlearn.idlang_gui.codeblock.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.idlang_gui.codeblock.BlockBase;
import mimuw.idlearn.idlang_gui.codeblock.CodeBlock;
import mimuw.idlearn.idlang_gui.codeblock.ResizableTextField;

public class IfHead extends CodeBlock {
	private final BlockBase base = new BlockBase(HEIGHT, Color.PURPLE);
	TextField condition;

	public String getCond() {
		return condition.getText();
	}

	/**
	 * Create a new If head
	 */
	public IfHead() {
		super();

		final Text ifText = new Text("If ");
		condition = new ResizableTextField(base);

		base.addChild(ifText);
		base.addChild(condition);

		this.getChildren().add(base);
	}

	/**
	 * Set the text in our if head
	 *
	 * @param text Condition text
	 */
	public void setText(String text) {
		condition.setText(text);
	}
}
