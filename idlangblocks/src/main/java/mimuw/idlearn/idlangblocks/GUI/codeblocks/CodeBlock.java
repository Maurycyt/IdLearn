package mimuw.idlearn.idlangblocks.GUI.codeblocks;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import mimuw.idlearn.achievements.AchievementManager;
import mimuw.idlearn.idlangblocks.GUI.CodeBox;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.blocktypes.*;
import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.userdata.CodeData;

import java.util.Timer;
import java.util.TimerTask;

public abstract class CodeBlock extends Group {
	public static final double HEIGHT = 50;
	private static final double INDENT = 40;

	private DragData dragData;
	private Pane dragged;
	private CodeBox codeBox;
	private int indent = 0;
	private static final Timer killer = new Timer();

	/**
	 * Return the height of this CodeBlock
	 *
	 * @return Height
	 */
	public double getEffectiveHeight() {
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
	public Expression convert() {
		throw new Error("Inconvertible");
	}


	/**
	 * Convert code block to corresponding CodeData
	 *
	 * @return Equivalent code data
	 */
	public CodeData saveFormat() {
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
		Pane parent = ((Pane) this.getParent());

		// Check if we should be added to the code section
		if (codeBox.shouldDrop(this.localToScene(0, 0))) {

			// Add us at the proper position
			codeBox.addChild(this.localToScene(0, 0).getY(), this);

			parent.getChildren().remove(this);

			AchievementManager.get(AchievementManager.CodeBlocksPlaced).increaseProgress();
		}
		else {
			this.relocate(0, 1000000);
			Node pray = this;
			killer.schedule(new TimerTask() {
				@Override
				public void run() {
					Platform.runLater(() -> parent.getChildren().remove(pray));
				}
			}, 1000);
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

		dragData.ghost = new Ghost(getEffectiveHeight());

		Pane parent = ((Pane) this.getParent());
		parent.getChildren().remove(this);
		dragged.getChildren().add(this);

		if (parent instanceof CodeBlockSpawner spawner) {
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
	 */
	public void makeDraggable(CodeBox codeBox, Pane dragged) {
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

	public static CodeBlock recreateBlock(CodeData data, CodeBox codeBox, Pane dragged) {
		switch (data.type) {
			case Assign -> {
				Assign assign = new Assign();
				assign.setEffectiveText(data.texts.get(0), data.texts.get(1));
				assign.makeDraggable(codeBox, dragged);
				return assign;
			}
			case Get -> {
				Get get = new Get();
				get.setEffectiveText(data.texts.get(0), data.texts.get(1), data.texts.get(2));
				get.makeDraggable(codeBox, dragged);
				return get;
			}
			case IfElse -> {
				IfElse ifElse = new IfElse();
				ifElse.setEffectiveText(data.texts.get(0));
				ifElse.setIfSegment(CodeSegment.recreateSegment(data.children.get(0), codeBox, dragged));
				ifElse.setElseSegment(CodeSegment.recreateSegment(data.children.get(1), codeBox, dragged));
				ifElse.makeVisible();
				ifElse.makeDraggable(codeBox, dragged);
				return ifElse;
			}
			case NewArray -> {
				NewArray newArray = new NewArray();
				newArray.setEffectiveText(data.texts.get(0), data.texts.get(1));
				newArray.makeDraggable(codeBox, dragged);
				return newArray;
			}
			case Operation -> {
				Operation operation = new Operation();
				operation.setEffectiveText(data.texts.get(0), data.texts.get(1), data.texts.get(3));
				operation.setType(data.texts.get(2));
				operation.makeDraggable(codeBox, dragged);
				return operation;
			}
			case Read -> {
				Read read = new Read();
				read.setEffectiveText(data.texts.get(0));
				read.makeDraggable(codeBox, dragged);
				return read;
			}
			case Write -> {
				Write write = new Write();
				write.setEffectiveText(data.texts.get(0));
				write.makeDraggable(codeBox, dragged);
				return write;
			}
			case Set -> {
				Set set = new Set();
				set.setEffectiveText(data.texts.get(0), data.texts.get(1), data.texts.get(2));
				set.makeDraggable(codeBox, dragged);
				return set;
			}
			case While -> {
				WhileBlock whileBlock = new WhileBlock();
				whileBlock.setEffectiveText(data.texts.get(0));
				whileBlock.setSegment(CodeSegment.recreateSegment(data.children.get(0), codeBox, dragged));
				whileBlock.makeVisible();
				whileBlock.makeDraggable(codeBox, dragged);
				return whileBlock;
			}
			case Segment -> throw new Error("This is a segment, not a codeblock");
			default -> throw new Error("Illegal type");
		}
	}


	public static void exit() {
		killer.cancel();
	}
}
