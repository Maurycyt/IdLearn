package mimuw.idlearn.idlangblocks.GUI.codeblocks.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.BlockBase;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.CodeBlock;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.ResizableTextField;
import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.InputHandler;
import mimuw.idlearn.idlang.logic.base.Type;
import mimuw.idlearn.idlang.logic.base.Variable;
import mimuw.idlearn.userdata.BlockType;
import mimuw.idlearn.userdata.CodeData;

public class Read extends CodeBlock {
	private final TextField varName;

	@Override
	public Expression convert() {
		String name = varName.getText();
		return new InputHandler(new Variable(Type.Long, name));
	}

	/**
	 * Create a new Read CodeBlock
	 */
	public Read() {
		super();

		final Text readText = new Text("Read ");
		varName = new ResizableTextField();

		BlockBase base = new BlockBase(HEIGHT, Color.web("#f7bd65", 1.0));
		base.addChild(readText);
		base.addChild(varName);

		this.getChildren().add(base);
	}

	/**
	 * Set the text in our read block
	 *
	 * @param text Variable name
	 */
	public void setEffectiveText(String text) {
		varName.setText(text);
	}

	@Override
	public CodeData saveFormat() {
		CodeData result = new CodeData();
		result.type = BlockType.Read;
		result.texts.add(varName.getText());
		return result;
	}
}
