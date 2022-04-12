package mimuw.idlearn.GUI.coding.codeblock.blocktypes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.GUI.coding.codeblock.BlockBase;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;
import mimuw.idlearn.GUI.coding.codeblock.ResizableTextField;
import mimuw.idlearn.GUI.coding.parser.StringToExpression;
import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.conversion.BoolToInt;
import mimuw.idlearn.language.keywords.Assignment;
import mimuw.idlearn.language.operators.TwoArgOperator;

public class Operation extends CodeBlock {
    private final BlockBase base = new BlockBase(HEIGHT, Color.GRAY);
    String oper;
    TextField oper1;
    TextField oper2;
    TextField result;

    /**
     * Create a new operation CodeBlock
     */
    public Operation() {
        super();

        result = new ResizableTextField(base);
        final Text equal = new Text(" = ");
        oper1 = new ResizableTextField(base);
        final Text space1 = new Text(" ");
        final Text space2 = new Text(" ");
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "+", "-", "×", "÷", ">", "<", "=="
                );
        final ComboBox dropDown = new ComboBox(options);
        dropDown.setValue("+");

        oper2 = new ResizableTextField(base);

        base.addChild(result);
        base.addChild(equal);
        base.addChild(oper1);
        base.addChild(space1);
        base.addChild(dropDown);
        base.addChild(space2);
        base.addChild(oper2);

        base.setCurrentWidth(10000);

        dropDown.widthProperty().addListener((obs, oldVal, newVal) -> {
            base.update();
        });

        this.getChildren().add(base);
    }

    /**
     * @return An equivalent expression
     */
    @Override
    public Expression<Void> convert() {
        Expression<Integer> op;
        Expression<Integer> arg1 = StringToExpression.parse(oper1.getText());
        Expression<Integer> arg2 = StringToExpression.parse(oper2.getText());
        String assignee = result.getText();
        op = switch (oper) {
            case "+" ->
                TwoArgOperator.newAdd(arg1, arg2);
            case "-" ->
                TwoArgOperator.newSubtract(arg1, arg2);
            case "×" ->
                TwoArgOperator.newMultiply(arg1, arg2);
            case "÷" ->
                TwoArgOperator.newDivide(arg1, arg2);
            case ">" ->
                new BoolToInt(TwoArgOperator.newGreater(arg1, arg2));
            case "<" ->
                new BoolToInt(TwoArgOperator.newLess(arg1, arg2));
            case "==" ->
                    new BoolToInt(TwoArgOperator.newEqual(arg1, arg2));
            default ->
                throw new Error("Invalid operand");
        };
        Expression<Void> result = new Assignment<>(assignee, op);
        return result;
    }

    /**
     * Set the text in our operation
     * @param text0 Where to put the result
     * @param text1 First operand
     * @param text2 Second operand
     */
    public void setText(String text0, String text1, String text2) {
        result.setText(text0);
        oper1.setText(text1);
        oper2.setText(text2);
    }
}
