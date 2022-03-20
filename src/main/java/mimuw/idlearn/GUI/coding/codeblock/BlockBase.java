package mimuw.idlearn.GUI.coding.codeblock;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BlockBase extends Group {

    final private Rectangle bg_ = new Rectangle();
    final private HBox content_ = new HBox();

    public BlockBase(double height, Color colour) {
        super();

        bg_.setHeight(height);
        bg_.setFill(colour);
        content_.setPrefHeight(height);
        content_.setAlignment(Pos.CENTER);

        this.getChildren().add(bg_);
        this.getChildren().add(content_);

        update();
    }

    // Recalculate size
    public double update() {

        double size = 20;
        var children = content_.getChildren();
        for (var child : children) {
            size += child.getLayoutBounds().getWidth();
        }
        content_.setPrefWidth(size);
        bg_.setWidth(size);
        return size;
    }

    // Add something new to the content box
    public void addChild(Node child) {
        content_.getChildren().add(child);
        update();
    }
}

