package mimuw.idlearn.GUI.coding.codeblock.blocktypes;

import javafx.scene.paint.Color;
import mimuw.idlearn.GUI.coding.codeblock.BlockBase;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;

public class End extends CodeBlock {
    private final BlockBase base;

    public End(Color colour) {
        super();

        base = new BlockBase(HEIGHT, colour);

        this.getChildren().add(base);
    }

}
