package mimuw.idlearn.idlang.GUI.codeblocks.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.idlang.GUI.codeblocks.BlockBase;
import mimuw.idlearn.idlang.GUI.codeblocks.CodeBlock;
import mimuw.idlearn.idlang.GUI.codeblocks.ResizableTextField;
import mimuw.idlearn.idlang.GUI.parser.StringToExpression;
import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.OutputHandler;
import mimuw.idlearn.packages.ProblemPackage;

public class Write extends CodeBlock {
	private final BlockBase base = new BlockBase(HEIGHT, Color.web("#d16662",1.0));
	private TextField varName;
	private ProblemPackage pkg;

	@Override
	public Expression convert() {
		String name = varName.getText();
		Expression val = StringToExpression.parse(name);
		return new OutputHandler(val);
	}

	/**
	 * Create a new Write CodeBlock
	 */
	public Write(ProblemPackage pkg) {
		super();

		this.pkg = pkg;
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
}
