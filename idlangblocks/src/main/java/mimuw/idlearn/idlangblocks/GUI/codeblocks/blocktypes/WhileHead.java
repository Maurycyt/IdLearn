package mimuw.idlearn.idlangblocks.GUI.codeblocks.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.BlockBase;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.CodeBlock;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.ResizableTextField;

public class WhileHead extends CodeBlock {
	private final TextField condition;

	public String getCond() {
		return condition.getText();
	}

	/**
	 * Create a new While head
	 */
	public WhileHead() {
		super();

		final Text whileText = new Text("While ");
		condition = new ResizableTextField();

		BlockBase base = new BlockBase(HEIGHT, Color.web("#d980c4", 1.0));
		base.addChild(whileText);
		base.addChild(condition);

		this.getChildren().add(base);
	}

	/**
	 * Set the text in our while head
	 *
	 * @param text Condition text
	 */
	public void setEffectiveText(String text) {
		condition.setText(text);
	}
}
