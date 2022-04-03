package mimuw.idlearn.GUI.coding.codeblock.blocktypes;

import javafx.scene.paint.Color;
import mimuw.idlearn.GUI.coding.codeblock.BlockBase;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;

public class Ghost extends CodeBlock {
    private int indentModAft;
    private int indentModBef;
    public Ghost(int indentBef, int indentAft, double height) {
        super();
        indentModBef = indentBef;
        indentModAft = indentAft;
        BlockBase base = new BlockBase(height, Color.TRANSPARENT);
        this.getChildren().add(base);
    }

    @Override
    public double getHeight() {
        return 0;
    }

    @Override
    public int getIndentModAft() {
        return indentModAft;
    }

    @Override
    public int getIndentModBef(){return indentModBef;}
}
