package mimuw.idlearn.idlangblocks.GUI.codeblocks.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.BlockBase;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.CodeBlock;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.ResizableTextField;
import mimuw.idlearn.idlangblocks.GUI.parser.StringToExpression;
import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.OutputHandler;
import mimuw.idlearn.packages.ProblemPackage;
import mimuw.idlearn.userdata.BlockType;
import mimuw.idlearn.userdata.CodeData;

public class Write extends CodeBlock {
	private final BlockBase base = new BlockBase(HEIGHT, Color.web("#d16662",1.0));
	private TextField varName;

	@Override
	public Expression convert() {
		String name = varName.getText();
		Expression val = StringToExpression.parse(name);
		return new OutputHandler(val);
	}

	/**
	 * Create a new Write CodeBlock
	 */
	public Write() {
		super();

		final Text readText = new Text("Write ");
		varName = new ResizableTextField();

		base.addChild(readText);
		base.addChild(varName);

		this.getChildren().add(base);
	}

	/**
	 * Set the text in our write block
	 *
	 * @param text Variable name
	 */
	public void setEffectiveText(String text) {
		varName.setText(text);
	}

	@Override
	public CodeData saveFormat() {
		CodeData result = new CodeData();
		result.type = BlockType.Write;
		result.texts.add(varName.getText());
		return result;
	}
}
