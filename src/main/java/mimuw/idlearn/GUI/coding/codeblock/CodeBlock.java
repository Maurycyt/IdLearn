package mimuw.idlearn.GUI.coding.codeblock;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import mimuw.idlearn.GUI.coding.CodeBox;
import mimuw.idlearn.GUI.coding.codeblock.blocktypes.Ghost;
import mimuw.idlearn.language.base.Expression;

public abstract class CodeBlock extends Group {
	public static final double HEIGHT = 50;
	private static final double INDENT = 40;

	private DragData dragData;
	private CodeBox codeBox;
	private Group dragged;
	private int indent = 0;
	private boolean isNew = true;

	/**
	 * Return the height of this CodeBlock
	 *
	 * @return Height
	 */
	public double getHeight() {
		return HEIGHT;
	}

	/**
	 * Set the indentation of this CodeBlock and its possible children
	 *
	 * @param ind Indentation
	 */
	public void setIndent(int ind) {
		assert (ind >= 0);
		indent = ind;
		this.setTranslateX(ind * INDENT);
	}

	/**
	 * Get the indentation of this CodeBlock
	 *
	 * @return Indentation
	 */
	public int getIndent() {
		return indent;
	}

	/**
	 * @return Whether this CodeBlock is a parent
	 */
	public boolean isParent() {
		return false;
	}

	/**
	 * Adds a new child to this CodeBlock
	 *
	 * @param position The child's absolute position
	 * @param child    The child
	 */
	public void addChild(double position, CodeBlock child) {
		throw new Error("Not a parent");
	}

	/**
	 * Removes a child
	 *
	 * @param child The child to be removed
	 * @return Whether this child was in us
	 */
	public boolean removeChild(CodeBlock child) {
		throw new Error("Not a parent");
	}

	/**
	 * @return The minimum relative Y value required to be put inside us
	 */
	public double insideBarrier() {
		throw new Error("Not a parent");
	}

	/**
	 * Convert code block to corresponding expression
	 *
	 * @return Equivalent expression
	 */
	public Expression<Void> convert() {
		throw new Error("Inconvertible");
	}

	/**
	 * Data we need to know when dragging a CodeBlock
	 */
	private static class DragData {
		// Initial mouse coordinates
		public double mouseAnchorX;
		public double mouseAnchorY;

		// Initial object coordinates
		public double initialX;
		public double initialY;

		// Our ghost
		public Ghost ghost;
	}


	protected CodeBlock() {
		super();
	}

	/**
	 * Re-places our ghost
	 */
	private void checkGhost() {
		codeBox.removeChild(dragData.ghost);
		if (codeBox.shouldDrop(this.localToScene(0, 0))) {
			codeBox.addChild(this.localToScene(0, 0).getY(), dragData.ghost);
		}
		codeBox.updateIndent();
	}

	/**
	 * Drops us, possibly into the CodeBox
	 */
	public void releaseMouse() {

		// Remove from parent
		dragged.getChildren().remove(this);

		// Check if we should be added to the code section
		if (codeBox.shouldDrop(this.localToScene(0, 0))) {

			// Add us at the proper position
			codeBox.addChild(this.localToScene(0, 0).getY(), this);
		}

		codeBox.removeChild(dragData.ghost);
		codeBox.updateIndent();
	}

	/**
	 * Picks us up
	 *
	 * @param mouseAX Starting mouse X position
	 * @param mouseAY Starting mouse Y position
	 */
	public void pressMouse(double mouseAX, double mouseAY) {

		// Set dragging info
		dragData.mouseAnchorX = mouseAX;
		dragData.mouseAnchorY = mouseAY;
		Point2D pos = this.localToScene(0, 0);
		dragData.initialX = pos.getX();
		dragData.initialY = pos.getY();

		dragData.ghost = new Ghost(getHeight());

		// Switch parent
		Pane parent = ((Pane) this.getParent());
		parent.getChildren().remove(this);
		dragged.getChildren().add(this);

		if (isNew) {
			isNew = false;
			CodeBlockSpawner spawner = (CodeBlockSpawner) parent;
			spawner.spawnBlock();
		}

		// Set proper location
		this.relocate(pos.getX(), pos.getY());

		// Reset indent
		this.setTranslateX(0);

		checkGhost();
	}

	/**
	 * Moves us around
	 *
	 * @param mouseX New mouse X position
	 * @param mouseY New mouse Y position
	 */
	public void moveMouse(double mouseX, double mouseY) {
		this.relocate(
				// Move to new proper location
				dragData.initialX + mouseX - dragData.mouseAnchorX,
				dragData.initialY + mouseY - dragData.mouseAnchorY
		);
		checkGhost();
	}

	/**
	 * Makes our CodeBlock draggable
	 *
	 * @param codeBox The CodeBox we can be dropped in
	 * @param dragged The parent for dragged CodeBlocks
	 */
	public void makeDraggable(CodeBox codeBox, Group dragged) {

		dragData = new DragData();
		this.codeBox = codeBox;
		this.dragged = dragged;

		this.addEventHandler(
				MouseEvent.MOUSE_PRESSED,
				mouseEvent -> {
					// Get mouse position
					double mouseAX = mouseEvent.getSceneX();
					double mouseAY = mouseEvent.getSceneY();
					// Do the pressing
					pressMouse(mouseAX, mouseAY);

					mouseEvent.consume();
				});

		this.addEventHandler(
				MouseEvent.MOUSE_DRAGGED,
				mouseEvent -> moveMouse(
						mouseEvent.getSceneX(), mouseEvent.getSceneY()
				));

		this.addEventHandler(
				MouseEvent.MOUSE_RELEASED,
				mouseEvent -> releaseMouse());
	}
}
