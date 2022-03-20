package mimuw.idlearn.GUI.coding.codeblock.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.GUI.coding.codeblock.BlockBase;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;
import mimuw.idlearn.GUI.coding.codeblock.ResizableTextField;

public abstract class Operation extends CodeBlock {
    private final BlockBase base = new BlockBase(HEIGHT, Color.GRAY);

    protected Operation(String oper) {
        super();

        final TextField result = new ResizableTextField(base);
        final Text equal = new Text(" = ");
        final TextField oper1 = new ResizableTextField(base);
        final Text operand = new Text(" " + oper + " ");
        final TextField oper2 = new ResizableTextField(base);

        base.addChild(result);
        base.addChild(equal);
        base.addChild(oper1);
        base.addChild(operand);
        base.addChild(oper2);

        this.getChildren().add(base);
    }
}
