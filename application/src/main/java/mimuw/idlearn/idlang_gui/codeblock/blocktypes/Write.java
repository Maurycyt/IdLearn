package mimuw.idlearn.idlang_gui.codeblock.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.idlang_gui.codeblock.BlockBase;
import mimuw.idlearn.idlang_gui.codeblock.CodeBlock;
import mimuw.idlearn.idlang_gui.codeblock.ResizableTextField;
import mimuw.idlearn.idlang.parser.StringToExpression;
import mimuw.idlearn.idlang.base.Expression;
import mimuw.idlearn.idlang.base.OutputHandler;
import mimuw.idlearn.problem_package_system.ProblemPackage;

public class Write extends CodeBlock {
	private final BlockBase base = new BlockBase(HEIGHT, Color.BLUE);
	TextField varName;
	ProblemPackage pkg;

	@Override
	public Expression<Void> convert() {
		String name = varName.getText();
		Expression<Integer> val = StringToExpression.parse(name);
		return new OutputHandler(pkg, val);
	}

	/**
	 * Create a new Write CodeBlock
	 */
	public Write(ProblemPackage pkg) {
		super();

		this.pkg = pkg;
		final Text readText = new Text("Write ");
		varName = new ResizableTextField(base);

		base.addChild(readText);
		base.addChild(varName);

		this.getChildren().add(base);
	}

	/**
	 * Set the text in our write block
	 *
	 * @param text Variable name
	 */
	public void setText(String text) {
		varName.setText(text);
	}
}
