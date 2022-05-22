package mimuw.idlearn.idlang.GUI.codeblocks.blocktypes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.idlang.GUI.codeblocks.BlockBase;
import mimuw.idlearn.idlang.GUI.codeblocks.CodeBlock;
import mimuw.idlearn.idlang.GUI.codeblocks.ResizableTextField;
import mimuw.idlearn.idlang.GUI.parser.StringToExpression;
import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.keywords.Assignment;
import mimuw.idlearn.idlang.logic.operators.TwoArgOperator;

public class Operation extends CodeBlock {
	private final BlockBase base = new BlockBase(HEIGHT, Color.web("#66c6e3",1.0));
	private TextField oper1;
	private TextField oper2;
	private TextField result;

	ChoiceBox<String> dropDown;

	/**
	 * Create a new operation CodeBlock
	 */
	public Operation() {
		super();

		result = new ResizableTextField();
		final Text equal = new Text(" = ");
		oper1 = new ResizableTextField();
		final Text space1 = new Text(" ");
		final Text space2 = new Text(" ");
		ObservableList<String> options =
				FXCollections.observableArrayList(
						"+", "-", "×", "÷", ">", "<", "==", "%"
				);
		dropDown = new ChoiceBox<>(options);
		dropDown.setValue("+");

		double max = 0;
		for (var option : options) {
			Text test = new Text(option);
			max = Math.max(max, test.getLayoutBounds().getWidth());
		}
		dropDown.setPrefWidth(max + 30);

		oper2 = new ResizableTextField();

		base.addChild(result);
		base.addChild(equal);
		base.addChild(oper1);
		base.addChild(space1);
		base.addChild(dropDown);
		base.addChild(space2);
		base.addChild(oper2);

		this.getChildren().add(base);
	}

	/**
	 * @return An equivalent expression
	 */
	@Override
	public Expression convert() {
		Expression op;
		Expression arg1 = StringToExpression.parse(oper1.getText());
		Expression arg2 = StringToExpression.parse(oper2.getText());
		String assignee = result.getText();
		op = switch (dropDown.getValue()) {
			case "+" -> TwoArgOperator.newAdd(arg1, arg2);
			case "-" -> TwoArgOperator.newSubtract(arg1, arg2);
			case "×" -> TwoArgOperator.newMultiply(arg1, arg2);
			case "÷" -> TwoArgOperator.newDivide(arg1, arg2);
			case ">" -> TwoArgOperator.newGreater(arg1, arg2);
			case "<" -> TwoArgOperator.newLess(arg1, arg2);
			case "==" -> TwoArgOperator.newEqual(arg1, arg2);
			case "%" -> TwoArgOperator.newModulo(arg1, arg2);
			default -> throw new Error("Invalid operand");
		};
		Expression result = new Assignment(assignee, op, false);
		return result;
	}

	/**
	 * Set the text in our operation
	 *
	 * @param text0 Where to put the result
	 * @param text1 First operand
	 * @param text2 Second operand
	 */
	public void setEffectiveText(String text0, String text1, String text2) {
		result.setText(text0);
		oper1.setText(text1);
		oper2.setText(text2);
	}

	public void setType(String type) {
		dropDown.setValue(type);
	}
}
