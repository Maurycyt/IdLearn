package mimuw.idlearn.GUI.coding;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;
import mimuw.idlearn.GUI.coding.codeblock.CodeSegment;

import static mimuw.idlearn.GUI.coding.codeblock.CodeBlock.HEIGHT;

public class CodeBox extends Group {

    private CodeSegment segment;

    public CodeBox() {
        super();
        segment = new CodeSegment();
        this.getChildren().add(segment);
    }



    public boolean removeChild(CodeBlock block) {

        return segment.removeChild(block);
    }

    public void addChild(double position, CodeBlock block) {

        segment.addChild(position, block);
    }

    /*private int calculateIndex(double toSceneY) {
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
    }*/

    // Updates indents for all blocks
    public void updateIndent() {
        segment.updateIndent();
    }

    protected CodeSegment getSegment() {
        return segment;
    }

    // Checks if a position is a valid drop off point for a codeblock
    public boolean shouldDrop(Point2D pos) {
        boolean isXOk = pos.getX() - this.localToScene(0, 0).getX() > -100;

        double relativeStartY = pos.getY() - this.localToScene(0, 0).getY();
        double relativeEndY = relativeStartY - this.getLayoutBounds().getHeight();

        boolean isYOk =  relativeEndY <= HEIGHT / 2 && relativeStartY >= - HEIGHT / 2;

        return isXOk && isYOk;
    }
}
