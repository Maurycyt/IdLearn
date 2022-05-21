package mimuw.idlearn.idlang.GUI.codeblocks.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.idlang.GUI.codeblocks.BlockBase;
import mimuw.idlearn.idlang.GUI.codeblocks.CodeBlock;
import mimuw.idlearn.idlang.GUI.codeblocks.ResizableTextField;
import mimuw.idlearn.idlang.GUI.parser.StringToExpression;
import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.keywords.MakeArray;

public class NewArray extends CodeBlock {

	private final BlockBase base = new BlockBase(HEIGHT, Color.web("#78d66b",1.0));

	TextField tabName;
	TextField size;

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

		tabName = new ResizableTextField(base);
		final Text lp = new Text("[");
		size = new ResizableTextField(base);
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
}

