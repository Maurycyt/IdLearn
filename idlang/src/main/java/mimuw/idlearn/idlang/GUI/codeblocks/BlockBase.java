package mimuw.idlearn.idlang.GUI.codeblocks;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BlockBase extends Group {

	final private Rectangle bg = new Rectangle();
	final private HBox content = new HBox();

/*	private static class Rect extends Region {
		@Override
		public void setHeight(double v) {
			super.setHeight(v);
		}
		@Override
		public void setWidth(double v) {
			super.setWidth(v);
		}
	}*/

	/**
	 * Create a new BlockBase
	 *
	 * @param height Height
	 * @param colour Background colour
	 */
	public BlockBase(double height, Color colour) {
		super();

		bg.setHeight(height);
		bg.setFill(colour);
		bg.setArcWidth(30.0);
		bg.setArcHeight(20.0);
		content.setPrefHeight(height);
		content.setAlignment(Pos.CENTER);

		this.getChildren().add(bg);
		this.getChildren().add(content);

		update();
	}

	/**
	 * Updates our width
	 *
	 * @return New width
	 */
	public double update() {

		double size = 20;
		var children = content.getChildren();
		for (var child : children) {
			size += child.getLayoutBounds().getWidth();
		}
		setCurrentWidth(size);
		return size;
	}

	public void setCurrentWidth(double width) {
		content.setPrefWidth(width);
		bg.setWidth(width);
	}

	/**
	 * Add something new to our content
	 *
	 * @param child New content
	 */
	public void addChild(Node child) {
		content.getChildren().add(child);
		update();
	}
}

