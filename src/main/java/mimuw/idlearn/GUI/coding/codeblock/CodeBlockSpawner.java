package mimuw.idlearn.GUI.coding.codeblock;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.function.Supplier;

public class CodeBlockSpawner extends AnchorPane {

    private final Pane codeBox;
    private final Group dragged;
    private final Supplier<CodeBlock> spawn;

    public void spawnBlock() {
        CodeBlock block = spawn.get();
        block.makeDraggable(codeBox, dragged);

        this.getChildren().add(block);
    }


    public CodeBlockSpawner(Pane codeBox, Group dragged, Supplier<CodeBlock> spawn){
        super();
        this.codeBox = codeBox;
        this.dragged = dragged;
        this.spawn = spawn;

        spawnBlock();


        // Listens if the child is being moved
        // Non-moving interactions consume the event so this won't be triggered
        this.addEventHandler(
                MouseEvent.MOUSE_PRESSED,
                mouseEvent -> spawnBlock()
        );
    }

}
