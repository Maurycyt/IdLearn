package mimuw.idlearn.GUI.coding.codeblock.blocktypes;

import javafx.scene.paint.Color;
import mimuw.idlearn.GUI.coding.codeblock.BlockBase;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;

public class Ghost extends CodeBlock {
    public Ghost() {
        super();
        BlockBase base = new BlockBase(HEIGHT, Color.TRANSPARENT);
        this.getChildren().add(base);
    }
}
