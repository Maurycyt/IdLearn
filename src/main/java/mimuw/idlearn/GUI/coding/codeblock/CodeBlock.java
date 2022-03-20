package mimuw.idlearn.GUI.coding.codeblock;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import mimuw.idlearn.GUI.coding.codeblock.blocktypes.Ghost;

public abstract class CodeBlock extends Group {
    protected double HEIGHT = 50;

    private DragData dragData;
    private Pane codeBox;
    private Group dragged;

    private static class DragData {
        // Initial mouse coordinates
        public double mouseAnchorX;
        public double mouseAnchorY;

        // Initial object coordinates
        public double initialX;
        public double initialY;

        // Our ghost
        public Ghost ghost;
        // The index of our ghost on the list, -1 if it's not there
        public int ghostIndex;
    }



    protected CodeBlock() {
        super();
    }

    private int calculateIndex() {
        double position = this.localToScene(0, 0).getY() - codeBox.localToScene(0, 0).getY();
        int index = (int)Math.round(position / HEIGHT);
        if (index > dragData.ghostIndex && dragData.ghostIndex != -1) {
            index++;
        }
        //System.out.println("Currently would be dropped at " + index);
        return index;
    }

    // TEMPORARY
    private boolean shouldDrop() {
        boolean isXOk = Math.abs(this.localToScene(0, 0).getX() - codeBox.localToScene(0, 0).getX()) < 100;

        int index = calculateIndex();
        boolean isYOk = index <= codeBox.getChildren().size() && index >= 0;

        if (isXOk && isYOk) {
            System.out.println("DROP OK");
        }
        return isXOk && isYOk;
    }

    private void removeGhost() {
        codeBox.getChildren().remove(dragData.ghostIndex);
        dragData.ghostIndex = -1;
    }

    private void addGhost(int index) {
        codeBox.getChildren().add(index, dragData.ghost);
        dragData.ghostIndex = index;
    }

    private void checkGhost() {
        if (shouldDrop()) {
            int index = calculateIndex();
            if (dragData.ghostIndex != -1 && dragData.ghostIndex != index) {
                removeGhost();
            }

            if (dragData.ghostIndex == -1) {
                index = calculateIndex();
                addGhost(index);
            }
        }
        else {
            if (dragData.ghostIndex != -1) {
                removeGhost();
            }
        }
    }

    protected void releaseMouse() {
        // Calculate our position in the code
        int index = calculateIndex();

        // Remove from parent
        dragged.getChildren().remove(this);

        // Check if we should be added to the code section
        if (shouldDrop()) {

            // Add us at the proper position
            codeBox.getChildren().add(index, this);
            if (index <= dragData.ghostIndex) {
                dragData.ghostIndex++;
            }

            System.out.println("Adding a child");
        }

        if (dragData.ghostIndex != -1) {
            removeGhost();
        }
    }

    protected void pressMouse(double mouseAX, double mouseAY) {
        // Set dragging info
        dragData.mouseAnchorX = mouseAX;
        dragData.mouseAnchorY = mouseAY;
        Point2D pos = this.localToScene(0, 0);
        dragData.initialX = pos.getX();
        dragData.initialY = pos.getY();

        dragData.ghost = new Ghost();
        dragData.ghostIndex = -1;

        // Switch parent
        Pane parent = ((Pane)this.getParent());
        parent.getChildren().remove(this);
        dragged.getChildren().add(this);

        // Set proper location
        this.relocate(pos.getX(), pos.getY());

        checkGhost();
    }

    protected void moveMouse(double mouseX, double mouseY) {
        this.relocate(
                // Move to new proper location
                dragData.initialX + mouseX - dragData.mouseAnchorX,
                dragData.initialY + mouseY - dragData.mouseAnchorY
        );
        checkGhost();
    }

    protected void makeDraggable(Pane codeBox, Group dragged) {

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
