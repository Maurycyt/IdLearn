package mimuw.idlearn.GUI.coding.codeblock.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
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
        InputHandler reader = new InputHandler(pkg, new Variable<Integer>(name));
        return reader;
    }

    /**
     * Create a new Read CodeBlock
     */
    public Read(ProblemPackage pkg) {
        super();

        this.pkg = pkg;
        varName = new ResizableTextField(base);

        base.addChild(varName);

        this.getChildren().add(base);
    }
}
