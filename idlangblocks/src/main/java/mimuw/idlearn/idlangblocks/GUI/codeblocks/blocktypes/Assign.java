package mimuw.idlearn.idlangblocks.GUI.codeblocks.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.BlockBase;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.CodeBlock;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.ResizableTextField;
import mimuw.idlearn.idlangblocks.GUI.parser.StringToExpression;
import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.keywords.Assignment;
import mimuw.idlearn.userdata.BlockType;
import mimuw.idlearn.userdata.CodeData;

public class Assign extends CodeBlock {

	private final BlockBase base = new BlockBase(HEIGHT, Color.web("#78d66b",1.0));
	private TextField varName;
	private TextField value;

	/**
	 * @return An equivalent expression
	 */
	@Override
	public Expression convert() {
		String name = varName.getText();
		String valueText = value.getText();
		Expression valueInt = StringToExpression.parse(valueText);
		Assignment assign = new Assignment(name, valueInt, true);
		return assign;
	}

	@Override
	public CodeData saveFormat() {
		CodeData result = new CodeData();
		result.type = BlockType.Assign;
		result.texts.add(varName.getText());
		result.texts.add(value.getText());
		return result;
	}

	/**
	 * Create a new Assignment CodeBlock
	 */
	public Assign() {
		super();

		varName = new ResizableTextField();
		final Text equal = new Text(" = ");
		value = new ResizableTextField();

		base.addChild(varName);
		base.addChild(equal);
		base.addChild(value);

		this.getChildren().add(base);
	}

	/**
	 * Set the text in our assign block
	 *
	 * @param text1 Variable name
	 * @param text2 Value
	 */
	public void setEffectiveText(String text1, String text2) {
		varName.setText(text1);
		value.setText(text2);
	}
}

