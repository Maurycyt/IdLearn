package mimuw.idlearn.idlang_gui.codeblock.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.idlang_gui.codeblock.BlockBase;
import mimuw.idlearn.idlang_gui.codeblock.CodeBlock;
import mimuw.idlearn.idlang_gui.codeblock.ResizableTextField;
import mimuw.idlearn.idlang.base.Expression;
import mimuw.idlearn.idlang.base.InputHandler;
import mimuw.idlearn.idlang.base.Variable;
import mimuw.idlearn.problem_package_system.ProblemPackage;

public class Read extends CodeBlock {
	private final BlockBase base = new BlockBase(HEIGHT, Color.GREEN);
	TextField varName;
	ProblemPackage pkg;

	@Override
	public Expression<Void> convert() {
		String name = varName.getText();
		return new InputHandler(pkg, new Variable<Integer>(name));
	}

	/**
	 * Create a new Read CodeBlock
	 */
	public Read(ProblemPackage pkg) {
		super();

		this.pkg = pkg;

		final Text readText = new Text("Read ");
		varName = new ResizableTextField(base);

		base.addChild(readText);
		base.addChild(varName);

		this.getChildren().add(base);
	}

	/**
	 * Set the text in our read block
	 *
	 * @param text Variable name
	 */
	public void setText(String text) {
		varName.setText(text);
	}
}
