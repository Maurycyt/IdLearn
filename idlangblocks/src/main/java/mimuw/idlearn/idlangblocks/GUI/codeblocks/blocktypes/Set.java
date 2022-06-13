package mimuw.idlearn.idlangblocks.GUI.codeblocks.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.BlockBase;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.CodeBlock;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.ResizableTextField;
import mimuw.idlearn.idlangblocks.GUI.parser.StringToExpression;
import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.Type;
import mimuw.idlearn.idlang.logic.base.Variable;
import mimuw.idlearn.idlang.logic.keywords.SetArray;
import mimuw.idlearn.userdata.BlockType;
import mimuw.idlearn.userdata.CodeData;


public class Set extends CodeBlock {

	private final TextField tabName;
	private final TextField index;
	private final TextField value;

	/**
	 * @return An equivalent expression
	 */
	@Override
	public Expression convert() {
		String valueString = value.getText();
		String table = tabName.getText();
		String index = this.index.getText();
		Expression tableVal = new Variable(Type.Table, table);

		Expression indexVal = StringToExpression.parse(index);
		Expression valueVal = StringToExpression.parse(valueString);

		return new SetArray(tableVal, indexVal, valueVal);
	}

	/**
	 * Create a new Get CodeBlock
	 */
	public Set() {
		super();

		tabName = new ResizableTextField();
		final Text at = new Text(" at ");
		index = new ResizableTextField();
		final Text set = new Text(" set to ");
		value = new ResizableTextField();

		BlockBase base = new BlockBase(HEIGHT, Color.web("#3dd9ac", 1.0));
		base.addChild(tabName);
		base.addChild(at);
		base.addChild(index);
		base.addChild(set);
		base.addChild(value);

		this.getChildren().add(base);
	}

	/**
	 * Set the text in our assign block
	 *
	 * @param value Value name
	 * @param table Table name
	 * @param index Table index
	 */
	public void setEffectiveText(String table, String index, String value) {
		this.value.setText(value);
		tabName.setText(table);
		this.index.setText(index);
	}

	@Override
	public CodeData saveFormat() {
		CodeData result = new CodeData();
		result.type = BlockType.Set;
		result.texts.add(tabName.getText());
		result.texts.add(index.getText());
		result.texts.add(value.getText());
		return result;
	}
}

