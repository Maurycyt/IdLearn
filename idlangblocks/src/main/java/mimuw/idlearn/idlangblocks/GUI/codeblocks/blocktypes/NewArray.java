package mimuw.idlearn.idlangblocks.GUI.codeblocks.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.BlockBase;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.CodeBlock;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.ResizableTextField;
import mimuw.idlearn.idlangblocks.GUI.parser.StringToExpression;
import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.keywords.MakeArray;
import mimuw.idlearn.userdata.BlockType;
import mimuw.idlearn.userdata.CodeData;

public class NewArray extends CodeBlock {

	private final BlockBase base = new BlockBase(HEIGHT, Color.web("#ff8fab",1.0));

	private TextField tabName;
	private TextField size;

	/**
	 * @return An equivalent expression
	 */
	@Override
	public Expression convert() {
		String table = tabName.getText();
		String size = this.size.getText();

		Expression sizeVal = StringToExpression.parse(size);

		return new MakeArray(table, sizeVal);
	}

	/**
	 * Create a new Get CodeBlock
	 */
	public NewArray() {
		super();

		tabName = new ResizableTextField();
		final Text lp = new Text("[");
		size = new ResizableTextField();
		final Text rp = new Text("]");

		base.addChild(tabName);
		base.addChild(lp);
		base.addChild(size);
		base.addChild(rp);

		this.getChildren().add(base);
	}

	/**
	 * Set the text in our assign block
	 *
	 * @param tabName Table name
	 * @param size Name of variable holding the table size
	 */
	public void setEffectiveText(String tabName, String size) {
		this.tabName.setText(tabName);
		this.size.setText(size);
	}

	@Override
	public CodeData saveFormat() {
		CodeData result = new CodeData();
		result.type = BlockType.NewArray;
		result.texts.add(tabName.getText());
		result.texts.add(size.getText());
		return result;
	}
}

