package mimuw.idlearn.GUI.coding.codeblock;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public abstract class CodeBlock extends Group {
    protected double HEIGHT = 50;

    private DragContext dragContext;
    private Pane codeBox;
    private Group dragged;

    private static class DragContext {
        // Initial mouse coordinates
        public double mouseAnchorX;
        public double mouseAnchorY;

        // Initial object coordinates
        public double initialX;
        public double initialY;
    }



    protected CodeBlock() {
        super();
    }

    private int calculateIndex(Pane codeBox) {
        double position = this.localToScene(0, 0).getY() - codeBox.localToScene(0, 0).getY();
        return (int)Math.round(position / HEIGHT);
    }

    // TEMPORARY
    private boolean shouldDrop(Pane codeBox) {
        return Math.abs(this.localToScene(0, 0).getX() - codeBox.localToScene(0, 0).getX()) < 100;
    }

    protected void releaseMouse() {
        // Calculate our position in the code
        int index = calculateIndex(codeBox);

        // Remove from parent
        dragged.getChildren().remove(this);

        // Check if we should be added to the code section
        if (shouldDrop(codeBox) && index >= 0 && index <= codeBox.getChildren().size()) {

            // Add us at the proper position
            codeBox.getChildren().add(index, this);
        }
    }

    protected void pressMouse(double mouseAX, double mouseAY) {
        // Set dragging info
        dragContext.mouseAnchorX = mouseAX;
        dragContext.mouseAnchorY = mouseAY;
        Point2D pos = this.localToScene(0, 0);
        dragContext.initialX = pos.getX();
        dragContext.initialY = pos.getY();

        // Switch parent
        Pane parent = ((Pane)this.getParent());
        parent.getChildren().remove(this);
        dragged.getChildren().add(this);

        // Set proper location
        this.relocate(pos.getX(), pos.getY());
    }

    protected void moveMouse(double mouseX, double mouseY) {
        this.relocate(
                // Move to new proper location
                dragContext.initialX + mouseX - dragContext.mouseAnchorX,
                dragContext.initialY + mouseY - dragContext.mouseAnchorY
        );
    }

    protected void makeDraggable(Pane codeBox, Group dragged) {

        dragContext = new DragContext();
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
