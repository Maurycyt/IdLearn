package mimuw.idlearn.GUI.coding;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;

public class CodeBox extends VBox {
    public CodeBox() {
        super();
    }

    public int calculateIndex(double toSceneY) {
        double position = toSceneY - this.localToScene(0, 0).getY();
        double sum = - CodeBlock.HEIGHT / 2;
        int i = 0;
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

    public void updateIndent() {
        int indent = 0;
        for (Node child : this.getChildren()) {
            CodeBlock block = (CodeBlock) child;
            if (block.getIndentMod() < 0) {
                indent += block.getIndentMod();
            }
            block.setIndent(indent);
            if (block.getIndentMod() > 0) {
                indent += block.getIndentMod();
            }
            indent = Math.max(indent, 0);
        }
    }

    // TEMPORARY
    public boolean shouldDrop(Point2D pos) {
        boolean isXOk = Math.abs(pos.getX() - this.localToScene(0, 0).getX()) < 100;

        int index = calculateIndex(pos.getY());
        boolean isYOk = index <= getChildren().size() && index >= 0;

        return isXOk && isYOk;
    }


}
