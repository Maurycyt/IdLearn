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
import mimuw.idlearn.idlang.logic.keywords.Assignment;
import mimuw.idlearn.idlang.logic.keywords.GetArray;
import mimuw.idlearn.userdata.BlockType;
import mimuw.idlearn.userdata.CodeData;

public class Get extends CodeBlock {

	private final BlockBase base = new BlockBase(HEIGHT, Color.web("#f2a963",1.0));
	private TextField varName;
	private TextField tabName;
	private TextField index;

	/**
	 * @return An equivalent expression
	 */
	@Override
	public Expression convert() {
		String varString = varName.getText();
		String tableString = tabName.getText();
		String indexString = this.index.getText();
		Expression tableVal = new Variable(Type.Table, tableString);

		Expression indexVal = StringToExpression.parse(indexString);

		return new Assignment(varString, new GetArray(tableVal, indexVal), false);
	}

	/**
	 * Create a new Get CodeBlock
	 */
	public Get() {
		super();

		varName = new ResizableTextField();
		final Text equal = new Text(" = get ");
		tabName = new ResizableTextField();
		final Text at = new Text(" at ");
		index = new ResizableTextField();

		base.addChild(varName);
		base.addChild(equal);
		base.addChild(tabName);
		base.addChild(at);
		base.addChild(index);

		this.getChildren().add(base);
	}

	/**
	 * Set the text in our assign block
	 *
	 * @param var Variable name
	 * @param table Table name
	 * @param index Table index
	 */
	public void setEffectiveText(String var, String table, String index) {
		varName.setText(var);
		tabName.setText(table);
		this.index.setText(index);
	}

	@Override
	public CodeData saveFormat() {
		CodeData result = new CodeData();
		result.type = BlockType.Get;
		result.texts.add(varName.getText());
		result.texts.add(tabName.getText());
		result.texts.add(index.getText());
		return result;
	}
}

