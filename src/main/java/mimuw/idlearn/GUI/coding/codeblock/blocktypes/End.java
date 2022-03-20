package mimuw.idlearn.GUI.coding.codeblock.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import mimuw.idlearn.GUI.coding.codeblock.BlockBase;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;
import mimuw.idlearn.GUI.coding.codeblock.ResizableTextField;

public class End extends CodeBlock {
    private final BlockBase base = new BlockBase(HEIGHT, Color.PURPLE);

    public End() {
        super();

        final TextField condition = new ResizableTextField(base);

        base.addChild(condition);

        this.getChildren().add(base);
    }

    @Override
    public int getIndentMod() {
        return -1;
    }
}
