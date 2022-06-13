package mimuw.idlearn.idlangblocks.GUI;

import javafx.geometry.Point2D;
import javafx.scene.layout.VBox;
import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.CodeBlock;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.CodeSegment;

public class CodeBox extends VBox {

	private CodeSegment segment;


	/**
	 * Create a new CodeBox
	 */
	public CodeBox() {
		super();
		segment = new CodeSegment();
		this.getChildren().add(segment);
	}
	public CodeBox(CodeSegment segment) {
		super();
		this.segment = segment;
		this.getChildren().add(segment);
	}


	/**
	 * Remove our child
	 *
	 * @param block The child to be removed
	 */
	public void removeChild(CodeBlock block) {
		segment.removeChild(block);
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
	public CodeSegment getSegment() {
		return segment;
	}
	public void setSegment(CodeSegment segment) {
		this.segment = segment;
		this.getChildren().remove(0);
		this.getChildren().add(segment);
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
	public Expression compile() {
		return segment.convert();
	}
}
