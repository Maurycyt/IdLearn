package mimuw.idlearn.GUI.coding;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;
import mimuw.idlearn.GUI.coding.codeblock.CodeSegment;
import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.Variable;
import mimuw.idlearn.language.environment.Scope;

import static mimuw.idlearn.GUI.coding.codeblock.CodeBlock.HEIGHT;

public class CodeBox extends Group {

    private CodeSegment segment;
    Button button = new Button("Convert");


    /**
     * Create a new CodeBox
     */
    public CodeBox() {
        super();
        segment = new CodeSegment();
        this.getChildren().add(segment);
        this.getChildren().add(button);
        button.setTranslateY(-50);

        button.setOnMousePressed(event -> {
            Expression<Void> exp = compile();
            Scope scope = new Scope();
            exp.evaluate(scope);
            System.out.println(new Variable<>("x").evaluate(scope).getValue());
        });
    }


    /**
     * Remove our child
     * @param block The child to be removed
     * @return Whether it was in us
     */
    public boolean removeChild(CodeBlock block) {

        return segment.removeChild(block);
    }

    /**
     * Add a new child
     * @param position The child's absolute Y position
     * @param block The child
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
    protected CodeSegment getSegment() {
        return segment;
    }

    /**
     * @param pos Position
     * @return Whether the position is a valid drop-off point
     */
    public boolean shouldDrop(Point2D pos) {
        boolean isXOk = pos.getX() - this.localToScene(0, 0).getX() > -100;

        double relativeStartY = pos.getY() - this.localToScene(0, 0).getY();
        double relativeEndY = relativeStartY - this.getLayoutBounds().getHeight();

        boolean isYOk =  relativeEndY <= HEIGHT / 2 && relativeStartY >= - HEIGHT / 2;

        return isXOk && isYOk;
    }

    /**
     *
     * @return
     */
    public Expression<Void> compile() {
        return segment.convert();
    }
}
