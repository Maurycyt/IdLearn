package mimuw.idlearn.idlangblocks.GUI.codeblocks.blocktypes;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.blocktypes.ElseHead;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.blocktypes.IfHead;
import mimuw.idlearn.idlang.logic.base.Type;
import mimuw.idlearn.idlang.logic.keywords.Block;
import mimuw.idlearn.idlang.logic.keywords.If;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.CodeBlock;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.CodeSegment;
import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.Variable;
import mimuw.idlearn.userdata.BlockType;
import mimuw.idlearn.userdata.CodeData;

public class IfElse extends CodeBlock {

	private final Pane content;
	private final IfHead ifHead;
	private final ElseHead elseHead;
	private final End foot;
	private CodeSegment ifSegment;
	private CodeSegment elseSegment;

	/**
	 * Create a new WhileBlock
	 */
	public IfElse() {
		super();
		content = new VBox();
		ifHead = new IfHead();
		elseHead = new ElseHead();
		elseHead.setVisible(false);
		elseHead.managedProperty().bind(elseHead.visibleProperty());
		foot = new End(Color.web("#aa6ee6",1.0));
		foot.setVisible(false);
		foot.managedProperty().bind(foot.visibleProperty());
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

		sum += ifHead.getEffectiveHeight();
		sum += ifSegment.giveHeight();
		sum += elseHead.getEffectiveHeight();
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
		return ifHead.getEffectiveHeight();
	}

	/**
	 * @return An equivalent expression
	 */
	@Override
	public Expression convert() {
		Block ifBody = ifSegment.convert();
		Block elseBody = elseSegment.convert();
		Expression condition = new Variable(Type.Long, ifHead.getCond());
		return new If(condition, ifBody, elseBody);
	}

	/**
	 * Return the height of this CodeBlock
	 *
	 * @return Height
	 */
	@Override
	public double getEffectiveHeight() {
		return (ifHead.getEffectiveHeight() +
				ifSegment.giveHeight() +
				elseHead.getEffectiveHeight() +
				elseSegment.getHeight() +
				foot.getEffectiveHeight()
		);
	}

	/**
	 * Set the text in our while
	 *
	 * @param text Condition text
	 */
	public void setEffectiveText(String text) {
		ifHead.setEffectiveText(text);
	}

	@Override
	public void releaseMouse() {
		super.releaseMouse();
		makeVisible();
	}

	@Override
	public CodeData saveFormat() {
		CodeData result = new CodeData();
		result.type = BlockType.IfElse;
		result.texts.add(ifHead.getCond());
		result.children.add(ifSegment.saveFormat());
		result.children.add(elseSegment.saveFormat());
		return result;
	}

	public void makeVisible() {
		elseHead.setVisible(true);
		foot.setVisible(true);
	}

	public void setIfSegment(CodeSegment segment) {
		content.getChildren().replaceAll((node) -> {
			if (node == ifSegment) {
				return segment;
			}
			else {
				return node;
			}});
		ifSegment = segment;
	}

	public void setElseSegment(CodeSegment segment) {
		content.getChildren().replaceAll((node) -> {
			if (node == elseSegment) {
					return segment;
			}
			else {
				return node;
			}});
		elseSegment = segment;
	}
}
