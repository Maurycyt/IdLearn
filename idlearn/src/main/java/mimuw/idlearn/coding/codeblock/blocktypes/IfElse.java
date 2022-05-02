package mimuw.idlearn.GUI.coding.codeblock.blocktypes;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;
import mimuw.idlearn.GUI.coding.codeblock.CodeSegment;
import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Value;
import mimuw.idlearn.language.base.Variable;
import mimuw.idlearn.language.conversion.IntToBool;
import mimuw.idlearn.language.keywords.*;
import mimuw.idlearn.language.operators.OneArgOperator;
import mimuw.idlearn.language.operators.TwoArgOperator;

public class IfElse extends CodeBlock {

	private final Pane content;
	private final IfHead ifHead;
	private final ElseHead elseHead;
	private final End foot;
	private final CodeSegment ifSegment;
	private final CodeSegment elseSegment;

	/**
	 * Create a new WhileBlock
	 */
	public IfElse() {
		super();
		content = new VBox();
		ifHead = new IfHead();
		elseHead = new ElseHead();
		foot = new End(Color.PURPLE);
		ifSegment = new CodeSegment();
		elseSegment = new CodeSegment();

		content.getChildren().add(ifHead);
		content.getChildren().add(ifSegment);
		content.getChildren().add(elseHead);
		content.getChildren().add(elseSegment);
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
	 *
	 * @param child The child to be removed
	 * @return Whether this child was in us
	 */
	@Override
	public boolean removeChild(CodeBlock child) {
		return ifSegment.removeChild(child) || elseSegment.removeChild(child);
	}

	/**
	 * Adds a new child to this CodeBlock
	 *
	 * @param position The child's absolute position
	 * @param child    The child
	 */
	@Override
	public void addChild(double position, CodeBlock child) {
		double relativeY = position - this.localToScene(0, 0).getY();

		// Offset
		double sum = -CodeBlock.HEIGHT / 2;

		sum += ifHead.getHeight();
		sum += ifSegment.giveHeight();
		sum += elseHead.getHeight();
		if (sum > relativeY) {
			ifSegment.addChild(position, child);
		} else {
			elseSegment.addChild(position, child);
		}
	}

	/**
	 * Set the indentation of this CodeBlock and its possible children
	 *
	 * @param ind Indentation
	 */
	@Override
	public void setIndent(int ind) {
		ifHead.setIndent(ind);
		elseHead.setIndent(ind);
		foot.setIndent(ind);
		ifSegment.updateIndent(ind + 1);
		elseSegment.updateIndent(ind + 1);
	}

	/**
	 * Get the indentation of this CodeBlock
	 *
	 * @return Indentation
	 */
	@Override
	public int getIndent() {
		return ifHead.getIndent();
	}

	@Override
	public double insideBarrier() {
		return ifHead.getHeight();
	}

	/**
	 * @return An equivalent expression
	 */
	@Override
	public Expression<Void> convert() {
		Block ifBody = ifSegment.convert();
		Block elseBody = elseSegment.convert();
		Expression<Boolean> condition = new IntToBool(new Variable<>(ifHead.getCond()));
		return new If(condition, ifBody, elseBody);
	}

	/**
	 * Return the height of this CodeBlock
	 *
	 * @return Height
	 */
	@Override
	public double getHeight() {
		return (
				ifHead.getHeight() + ifSegment.giveHeight() + elseHead.getHeight() +
						elseSegment.getHeight() + foot.getHeight()
		);
	}

	/**
	 * Set the text in our while
	 *
	 * @param text Condition text
	 */
	public void setText(String text) {
		ifHead.setText(text);
	}
}
