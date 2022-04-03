package mimuw.idlearn.GUI.coding.codeblock.blocktypes;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;
import mimuw.idlearn.GUI.coding.codeblock.CodeSegment;

public class BlockParent extends CodeBlock {

    private Pane content;
    private CodeBlock head;
    private CodeBlock foot;
    private CodeSegment segment;

    public BlockParent() {
        super();
        content = new VBox();
        head = new While();
        foot = new End(Color.AQUA);
        segment = new CodeSegment();

        content.getChildren().add(head);
        content.getChildren().add(segment);
        content.getChildren().add(foot);

        this.getChildren().add(content);
    }

    @Override
    public boolean isParent() {
        return true;
    }

    @Override
    public boolean removeChild(CodeBlock child) {
        return segment.removeChild(child);
    }

    @Override
    public void addChild(double position, CodeBlock child) {
        segment.addChild(position, child);
    }

    @Override
    public void updateIndent(int base) {
        head.setIndent(base);
        foot.setIndent(base);
        segment.updateIndent(base + 1);
    }

    @Override
    public double insideBarrier() {
        return head.getHeight();
    }

    @Override
    public double getHeight() {
        return head.getHeight() + segment.giveHeight() + foot.getHeight();
    }
}
