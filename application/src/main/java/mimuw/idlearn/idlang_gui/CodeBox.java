package mimuw.idlearn.idlang_gui;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import mimuw.idlearn.idlang_gui.codeblock.CodeBlock;
import mimuw.idlearn.idlang_gui.codeblock.CodeSegment;
import mimuw.idlearn.idlang.base.Expression;

public class CodeBox extends Group {

	private CodeSegment segment;


	/**
	 * Create a new CodeBox
	 */
	public CodeBox() {
		super();
		segment = new CodeSegment();
		this.getChildren().add(segment);
	}


	/**
	 * Remove our child
	 *
	 * @param block The child to be removed
	 * @return Whether it was in us
	 */
	public boolean removeChild(CodeBlock block) {
		return segment.removeChild(block);
	}

	/**
	 * Add a new child
	 *
	 * @param position The child's absolute Y position
	 * @param block    The child
	 */
	public void addChild(double position, CodeBlock block) {

		segment.addChild(position, block);
	}

	/**
	 * Updates indents for all blocks
	 */
	public void updateIndent() {
		segment.updateIndent();
	}

	/**
	 * @return Our code segment
	 */
	protected CodeSegment getSegment() {
		return segment;
	}

	/**
	 * @param pos Position
	 * @return Whether the position is a valid drop-off point
	 */
	public boolean shouldDrop(Point2D pos) {
		boolean isXOk = pos.getX() - this.localToScene(0, 0).getX() > -100;

		double relativeStartY = pos.getY() - this.localToScene(0, 0).getY();
		double relativeEndY = relativeStartY - this.getLayoutBounds().getHeight();

		boolean isYOk = relativeEndY <= CodeBlock.HEIGHT / 2 && relativeStartY >= -CodeBlock.HEIGHT / 2;

		return isXOk && isYOk;
	}

	/**
	 * Compiles the CodeBox into code
	 *
	 * @return Equivalent expression
	 */
	public Expression<Void> compile() {
		return segment.convert();
	}
}
