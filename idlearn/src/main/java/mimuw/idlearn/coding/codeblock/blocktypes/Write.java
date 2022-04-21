package mimuw.idlearn.GUI.coding.codeblock.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import mimuw.idlearn.GUI.coding.codeblock.BlockBase;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;
import mimuw.idlearn.GUI.coding.codeblock.ResizableTextField;
import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.OutputHandler;
import mimuw.idlearn.language.base.Variable;
import mimuw.idlearn.problems.ProblemPackage;

public class Write extends CodeBlock {
    private final BlockBase base = new BlockBase(HEIGHT, Color.BLACK);
    TextField varName;
    ProblemPackage pkg;

    @Override
    public Expression<Void> convert() {
        String name = varName.getText();
        OutputHandler writer = new OutputHandler(pkg, new Variable<Integer>(name));
        return writer;
    }

    /**
     * Create a new Write CodeBlock
     */
    public Write(ProblemPackage pkg) {
        super();

        this.pkg = pkg;
        varName = new ResizableTextField(base);

        base.addChild(varName);

        this.getChildren().add(base);
    }

    /**
     * Set the text in our write block
     * @param text Variable name
     */
    public void setText(String text) {
        varName.setText(text);
    }
}
