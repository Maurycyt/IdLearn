package mimuw.idlearn.GUI.coding;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;

public class CodeBox extends VBox {
    public CodeBox() {
        super();
    }

    public void remove(int index) {
        this.getChildren().remove(index);
    }

    public void add(int index, CodeBlock block) {
        this.getChildren().add(index, block);
    }

    public int calculateIndex(double toSceneY) {
        // Get block position relative to the box
        double position = toSceneY - this.localToScene(0, 0).getY();

        // Offset
        double sum = - CodeBlock.HEIGHT / 2;
        int i = 0;

        // Go until we reach where we should be
        for (Node child : this.getChildren()) {
            CodeBlock block = (CodeBlock) child;
            sum += block.getHeight();
            if (sum > position) {
                break;
            }
            i++;
        }
        return i;
    }

    // Updates indents for all blocks
    public void updateIndent() {
        int indent = 0;
        for (Node child : this.getChildren()) {
            CodeBlock block = (CodeBlock) child;
            indent += block.getIndentModBef();
            indent = Math.max(indent, 0);

            block.setIndent(indent);

            indent += block.getIndentModAft();
            indent = Math.max(indent, 0);
        }
    }

    // Checks if a position is a valid drop off point for a codeblock
    public boolean shouldDrop(Point2D pos) {
        boolean isXOk = Math.abs(pos.getX() - this.localToScene(0, 0).getX()) < 100;

        int index = calculateIndex(pos.getY());
        boolean isYOk = index <= getChildren().size() && index >= 0;

        return isXOk && isYOk;
    }
}
