package mimuw.idlearn.idlangblocks.GUI.codeblocks;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.keywords.Block;
import mimuw.idlearn.idlangblocks.GUI.CodeBox;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.blocktypes.Ghost;
import mimuw.idlearn.userdata.BlockType;
import mimuw.idlearn.userdata.CodeData;

import java.util.ArrayList;

public class CodeSegment extends VBox {
	/**
	 * Removes a CodeBlock from this segment
	 *
	 * @param block A CodeBlock to be removed
	 * @return Whether the CodeBlock was in this segment
	 */
	public boolean removeChild(CodeBlock block) {

		boolean removed = this.getChildren().remove(block);

		if (!removed) {
			for (Node child : this.getChildren()) {
				CodeBlock current_block = (CodeBlock) child;
				if (current_block.isParent()) {
					removed = current_block.removeChild(block);
					if (removed) {
						break;
					}
				}
			}
		}

		return removed;
	}

	/**
	 * Adds a child to this segment
	 *
	 * @param position Absolute Y position of the new child
	 * @param block    The CodeBlock to be added
	 */
	public void addChild(double position, CodeBlock block) {

		// Get block position relative to the box
		double relativeY = position - this.localToScene(0, 0).getY();

		// Offset
		double sum = -CodeBlock.HEIGHT / 2;
		double lastSum;
		int i = 0;

		// Go until we reach where we should be
		for (Node child : this.getChildren()) {
			CodeBlock current_block = (CodeBlock) child;
			lastSum = sum;
			sum += current_block.getEffectiveHeight();
			if (sum > relativeY) {

				if (current_block.isParent() && relativeY - lastSum >= current_block.insideBarrier()) {
					current_block.addChild(position, block);
					return;
				}
				break;
			}
			i++;
		}
		this.getChildren().add(i, block);
	}


	// Currently somewhat redundant, only needs to give indents to the current block

	/**
	 * Updates indentations
	 *
	 * @param base indentation of the whole block
	 */
	public void updateIndent(int base) {
		for (Node child : this.getChildren()) {
			CodeBlock block = (CodeBlock) child;
			block.setIndent(base);
		}
	}

	/**
	 * Updates indents with the whole block's indentation being 0
	 */
	public void updateIndent() {
		updateIndent(0);
	}

	/**
	 * Calculates the height of the segment
	 *
	 * @return height
	 */
	public double giveHeight() {
		double sum = 0;
		for (Node child : this.getChildren()) {
			CodeBlock currentBlock = (CodeBlock) child;
			sum += currentBlock.getEffectiveHeight();
		}
		return sum;
	}

	/**
	 * Converts segment into an expression
	 *
	 * @return Equivalent expression
	 */
	public Block convert() {
		ArrayList<Expression> expressions = new ArrayList<>();
		for (Node child : this.getChildren()) {
			CodeBlock currentBlock = (CodeBlock) child;
			expressions.add(currentBlock.convert());
		}
		Block block = new Block(expressions);
		return block;
	}

	public CodeData saveFormat() {
		CodeData result = new CodeData();
		result.type = BlockType.Segment;
		for (Node child : this.getChildren()) {
			CodeBlock currentBlock = (CodeBlock) child;
			if (! (child instanceof Ghost)) {
				result.children.add(currentBlock.saveFormat());
			}
		}
		return result;
	}

	static public CodeSegment recreateSegment(CodeData data, CodeBox codeBox, Pane dragged) {
		if (data.type != BlockType.Segment) {
			throw new Error("This isn't a code segment data");
		}
		CodeSegment result = new CodeSegment();
		for (CodeData codeData : data.children) {
			CodeBlock block = CodeBlock.recreateBlock(codeData, codeBox, dragged);
			result.getChildren().add(block);
		}
		return result;
	}
}
