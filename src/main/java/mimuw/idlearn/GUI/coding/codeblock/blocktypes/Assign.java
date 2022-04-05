package mimuw.idlearn.GUI.coding.codeblock.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.GUI.coding.codeblock.BlockBase;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;
import mimuw.idlearn.GUI.coding.codeblock.ResizableTextField;

public class Assign extends CodeBlock {

    private final BlockBase base = new BlockBase(HEIGHT, Color.GRAY);

    /**
     * Create a new Assignment CodeBlock
     */
    public Assign() {
        super();

        final TextField varName = new ResizableTextField(base);
        final Text equal = new Text(" = ");
        final TextField value = new ResizableTextField(base);

        base.addChild(varName);
        base.addChild(equal);
        base.addChild(value);

        this.getChildren().add(base);
    }

}

