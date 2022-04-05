package mimuw.idlearn.GUI.coding.codeblock.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.GUI.coding.codeblock.BlockBase;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;
import mimuw.idlearn.GUI.coding.codeblock.ResizableTextField;
import mimuw.idlearn.GUI.coding.parser.StringToExpression;
import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.keywords.Assignment;

public class Assign extends CodeBlock {

    private final BlockBase base = new BlockBase(HEIGHT, Color.GRAY);
    TextField varName;
    TextField value;

    @Override
    public Expression<Void> convert() {
        String name = varName.getText();
        String valueText = value.getText();
        Expression<Integer> valueInt = StringToExpression.parse(valueText);
        Assignment<Integer> assign = new Assignment<>(name, valueInt);
        return assign;
    }

    /**
     * Create a new Assignment CodeBlock
     */
    public Assign() {
        super();

        varName = new ResizableTextField(base);
        final Text equal = new Text(" = ");
        value = new ResizableTextField(base);

        base.addChild(varName);
        base.addChild(equal);
        base.addChild(value);

        this.getChildren().add(base);
    }

}

