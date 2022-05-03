package mimuw.idlearn.idlangGui.codeblock.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.idlangGui.codeblock.BlockBase;
import mimuw.idlearn.idlangGui.codeblock.CodeBlock;
import mimuw.idlearn.idlangGui.codeblock.ResizableTextField;

public class WhileHead extends CodeBlock {
	private final BlockBase base = new BlockBase(HEIGHT, Color.AQUA);
	TextField condition;

	public String getCond() {
		return condition.getText();
	}

	/**
	 * Create a new While head
	 */
	public WhileHead() {
		super();

		final Text whileText = new Text("While ");
		condition = new ResizableTextField(base);

		base.addChild(whileText);
		base.addChild(condition);

		this.getChildren().add(base);
	}

	/**
	 * Set the text in our while head
	 *
	 * @param text Condition text
	 */
	public void setText(String text) {
		condition.setText(text);
	}
}
