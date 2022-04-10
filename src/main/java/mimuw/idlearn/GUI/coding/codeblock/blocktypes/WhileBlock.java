package mimuw.idlearn.GUI.coding.codeblock.blocktypes;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;
import mimuw.idlearn.GUI.coding.codeblock.CodeSegment;
import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.base.Variable;
import mimuw.idlearn.language.keywords.*;
import mimuw.idlearn.language.operators.OneArgOperator;
import mimuw.idlearn.language.operators.TwoArgOperator;

public class WhileBlock extends CodeBlock {

    private Pane content;
    private WhileHead head;
    private CodeBlock foot;
    private CodeSegment segment;

    /**
     * Create a new WhileBlock
     */
    public WhileBlock() {
        super();
        content = new VBox();
        head = new WhileHead();
        foot = new End(Color.AQUA);
        segment = new CodeSegment();

        content.getChildren().add(head);
        content.getChildren().add(segment);
        content.getChildren().add(foot);

        this.getChildren().add(content);
    }

    /**
     * @return Whether this CodeBlock is a parent
     */
    @Override
    public boolean isParent() {
        return true;
    }

    /**
     * Removes a child
     * @param child The child to be removed
     * @return Whether this child was in us
     */
    @Override
    public boolean removeChild(CodeBlock child) {
        return segment.removeChild(child);
    }

    /**
     * Adds a new child to this CodeBlock
     * @param position The child's absolute position
     * @param child The child
     */
    @Override
    public void addChild(double position, CodeBlock child) {
        segment.addChild(position, child);
    }

    /**
     * Set the indentation of this CodeBlock and its possible children
     * @param ind Indentation
     */
    @Override
    public void setIndent(int ind) {
        head.setIndent(ind);
        foot.setIndent(ind);
        segment.updateIndent(ind + 1);
    }

    /**
     * Get the indentation of this CodeBlock
     * @return Indentation
     */
     @Override
     public int getIndent() {
        return head.getIndent();
     }

    @Override
    public double insideBarrier() {
        return head.getHeight();
    }

    /**
     * @return An equivalent expression
     */
    @Override
    public Expression<Void> convert() {
        Block body = segment.convert();
        Expression<Boolean> condition = TwoArgOperator.newEqual(new Variable<>(head.getCond()), new Value<>(0));
        While result = new While(OneArgOperator.newNot(condition), body);
        return result;
    }

    /**
     * Return the height of this CodeBlock
     * @return Height
     */
    @Override
    public double getHeight() {
        return head.getHeight() + segment.giveHeight() + foot.getHeight();
    }

    /**
     * Set the text in our while
     * @param text Condition text
     */
    public void setText(String text) {
        head.setText(text);
    }
}
