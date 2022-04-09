package mimuw.idlearn.GUI.coding.codeblock.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import mimuw.idlearn.GUI.coding.codeblock.BlockBase;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;
import mimuw.idlearn.GUI.coding.codeblock.ResizableTextField;
import mimuw.idlearn.language.base.Expression;

public class WhileHead extends CodeBlock {
    private final BlockBase base = new BlockBase(HEIGHT, Color.BLUE);
    TextField condition;

    @Override
    public Expression<Void> convert() {
        throw new Error("Inconvertible");
    }

    public String getCond() {
        return condition.getText();
    }

    /**
     * Create a new While head
     */
    public WhileHead() {
        super();

        condition = new ResizableTextField(base);

        base.addChild(condition);

        this.getChildren().add(base);
    }

    /**
     * Set the text in our while head
     * @param text Condition text
     */
    public void setText(String text) {
        condition.setText(text);
    }
}
