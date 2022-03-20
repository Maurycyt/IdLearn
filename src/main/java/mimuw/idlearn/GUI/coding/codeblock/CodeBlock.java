package mimuw.idlearn.GUI.coding.codeblock;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import mimuw.idlearn.GUI.coding.CodeBox;
import mimuw.idlearn.GUI.coding.codeblock.blocktypes.Ghost;

public abstract class CodeBlock extends Group {
    public static final double HEIGHT = 50;
    private static final double INDENT = 40;

    private DragData dragData;
    private CodeBox codeBox;
    private Group dragged;
    private int indent = 0;

    public double getHeight() {
        return HEIGHT;
    }

    public int getIndentMod() {
        return 0;
    }

    public void setIndent(int ind) {
        assert(ind >= 0);
        indent = ind;
        this.setTranslateX(ind * INDENT);
    }

    public int getIndent() {
        return indent;
    }

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





    private void removeGhost() {
        codeBox.remove(dragData.ghostIndex);
        dragData.ghostIndex = -1;
        codeBox.updateIndent();
    }

    private void addGhost(int index) {
        codeBox.add(index, dragData.ghost);
        dragData.ghostIndex = index;
        codeBox.updateIndent();
    }

    private void checkGhost() {
        if (codeBox.shouldDrop(this.localToScene(0, 0))) {
            int index = codeBox.calculateIndex(this.localToScene(0, 0).getY());
            if (dragData.ghostIndex != -1 && dragData.ghostIndex != index) {
                removeGhost();
            }

            if (dragData.ghostIndex == -1) {
                index = codeBox.calculateIndex(this.localToScene(0, 0).getY());
                addGhost(index);
            }
        }
        else {
            if (dragData.ghostIndex != -1) {
                removeGhost();
            }
        }
    }

    public void releaseMouse() {
        // Calculate our position in the code
        int index = codeBox.calculateIndex(this.localToScene(0, 0).getY());

        // Remove from parent
        dragged.getChildren().remove(this);

        // Check if we should be added to the code section
        if (codeBox.shouldDrop(this.localToScene(0, 0))) {

            // Add us at the proper position
            codeBox.add(index, this);
            if (index <= dragData.ghostIndex) {
                dragData.ghostIndex++;
            }
        }

        if (dragData.ghostIndex != -1) {
            removeGhost();
        }
    }

    public void pressMouse(double mouseAX, double mouseAY) {
        // Set dragging info
        dragData.mouseAnchorX = mouseAX;
        dragData.mouseAnchorY = mouseAY;
        Point2D pos = this.localToScene(0, 0);
        dragData.initialX = pos.getX();
        dragData.initialY = pos.getY();

        dragData.ghost = new Ghost(getIndentMod());
        dragData.ghostIndex = -1;

        // Switch parent
        Pane parent = ((Pane)this.getParent());
        parent.getChildren().remove(this);
        dragged.getChildren().add(this);

        // Set proper location
        this.relocate(pos.getX(), pos.getY());

        // Reset indent
        this.setTranslateX(0);

        checkGhost();
    }

    public void moveMouse(double mouseX, double mouseY) {
        this.relocate(
                // Move to new proper location
                dragData.initialX + mouseX - dragData.mouseAnchorX,
                dragData.initialY + mouseY - dragData.mouseAnchorY
        );
        checkGhost();
    }

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
