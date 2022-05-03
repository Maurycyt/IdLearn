package mimuw.idlearn.idlang.GUI.codeblocks.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.idlang.GUI.codeblocks.BlockBase;
import mimuw.idlearn.idlang.GUI.codeblocks.CodeBlock;
import mimuw.idlearn.idlang.GUI.codeblocks.ResizableTextField;
import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.InputHandler;
import mimuw.idlearn.idlang.logic.base.Variable;
import mimuw.idlearn.packages.ProblemPackage;

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
