package mimuw.idlearn.idlang.GUI.codeblocks.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.idlang.GUI.codeblocks.BlockBase;
import mimuw.idlearn.idlang.GUI.codeblocks.CodeBlock;
import mimuw.idlearn.idlang.GUI.codeblocks.ResizableTextField;

public class IfHead extends CodeBlock {
	private final BlockBase base = new BlockBase(HEIGHT, Color.web("#aa6ee6",1.0));
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
		condition = new ResizableTextField();

		base.addChild(ifText);
		base.addChild(condition);

		this.getChildren().add(base);
	}

	/**
	 * Set the text in our if head
	 *
	 * @param text Condition text
	 */
	public void setEffectiveText(String text) {
		condition.setText(text);
	}
}
