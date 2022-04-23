package mimuw.idlearn.GUI.coding.codeblock.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.GUI.coding.codeblock.BlockBase;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;
import mimuw.idlearn.GUI.coding.codeblock.ResizableTextField;
import mimuw.idlearn.language.base.Expression;

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
