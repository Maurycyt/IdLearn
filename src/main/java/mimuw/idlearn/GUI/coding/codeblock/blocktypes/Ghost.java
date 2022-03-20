package mimuw.idlearn.GUI.coding.codeblock.blocktypes;

import javafx.scene.paint.Color;
import mimuw.idlearn.GUI.coding.codeblock.BlockBase;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;

public class Ghost extends CodeBlock {
    private int indentMod;
    public Ghost(int indent) {
        super();
        indentMod = indent;
        BlockBase base = new BlockBase(HEIGHT, Color.TRANSPARENT);
        this.getChildren().add(base);
    }

    @Override
    public double getHeight() {
        return 0;
    }

    @Override
    public int getIndentMod() {
        return indentMod;
    }
}
