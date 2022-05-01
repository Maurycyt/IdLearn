package mimuw.idlearn.GUI.coding.codeblock.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.GUI.coding.codeblock.BlockBase;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;
import mimuw.idlearn.GUI.coding.codeblock.ResizableTextField;
import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.InputHandler;
import mimuw.idlearn.language.base.Variable;
import mimuw.idlearn.problems.ProblemPackage;

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
