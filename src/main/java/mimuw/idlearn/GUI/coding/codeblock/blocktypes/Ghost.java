package mimuw.idlearn.GUI.coding.codeblock.blocktypes;

import javafx.scene.paint.Color;
import mimuw.idlearn.GUI.coding.codeblock.BlockBase;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;

public class Ghost extends CodeBlock {
    public Ghost(double height) {
        super();

        BlockBase base = new BlockBase(height, Color.TRANSPARENT);
        this.getChildren().add(base);
    }

    @Override
    public double getHeight() {
        return 0;
    }

}
