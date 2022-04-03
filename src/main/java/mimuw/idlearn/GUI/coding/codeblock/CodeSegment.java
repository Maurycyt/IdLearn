package mimuw.idlearn.GUI.coding.codeblock;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class CodeSegment extends VBox {

    public boolean removeChild(CodeBlock block) {

        boolean removed = this.getChildren().remove(block);

        if (!removed) {
            for (Node child : this.getChildren()) {
                CodeBlock current_block = (CodeBlock) child;
                if (current_block.isParent()) {
                    removed = current_block.removeChild(block);
                    if (removed) {
                        break;
                    }
                }
            }
        }

        return removed;
    }

    public void addChild(double position, CodeBlock block) {

        // Get block position relative to the box
        double relativeY = position - this.localToScene(0, 0).getY();

        // Offset
        double sum = - CodeBlock.HEIGHT / 2;
        double lastSum;
        int i = 0;

        // Go until we reach where we should be
        for (Node child : this.getChildren()) {
            CodeBlock current_block = (CodeBlock) child;
            lastSum = sum;
            sum += current_block.getHeight();
            if (sum > relativeY) {

                if (current_block.isParent() && relativeY - lastSum >= current_block.insideBarrier()) {
                    current_block.addChild(position, block);
                    return;
                }
                break;
            }
            i++;
        }

        this.getChildren().add(i, block);
    }

    // Updates indents for all blocks
    public void updateIndent(int base) {
        int indent = base;
        for (Node child : this.getChildren()) {
            CodeBlock block = (CodeBlock) child;

            if (block.isParent()) {
                block.updateIndent(base);
            }
            else {
                block.setIndent(base);
            }
        }
    }

    public void updateIndent() {
        updateIndent(0);
    }

    public double giveHeight() {
        double sum = 0;
        for (Node child : this.getChildren()) {
            CodeBlock current_block = (CodeBlock) child;
            sum += current_block.getHeight();
        }
        return sum;
    }
}
