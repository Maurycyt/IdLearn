package mimuw.idlearn.idlang.GUI.codeblocks.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.idlang.GUI.codeblocks.BlockBase;
import mimuw.idlearn.idlang.GUI.codeblocks.CodeBlock;
import mimuw.idlearn.idlang.GUI.codeblocks.ResizableTextField;
import mimuw.idlearn.idlang.GUI.parser.StringToExpression;
import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.Type;
import mimuw.idlearn.idlang.logic.base.Variable;
import mimuw.idlearn.idlang.logic.keywords.SetArray;


public class Set extends CodeBlock {

	private final BlockBase base = new BlockBase(HEIGHT, Color.web("#3dd9ac",1.0));
	TextField tabName;
	TextField index;
	TextField value;

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

		tabName = new ResizableTextField(base);
		final Text at = new Text(" at ");
		index = new ResizableTextField(base);
		final Text set = new Text(" set to ");
		value = new ResizableTextField(base);

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
}

