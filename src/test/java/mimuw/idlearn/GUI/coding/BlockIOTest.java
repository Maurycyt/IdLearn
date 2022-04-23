package mimuw.idlearn.GUI.coding;

import javafx.application.Platform;
import javafx.scene.Group;
import mimuw.idlearn.GUI.coding.codeblock.blocktypes.Operation;
import mimuw.idlearn.GUI.coding.codeblock.blocktypes.Read;
import mimuw.idlearn.GUI.coding.codeblock.blocktypes.Write;
import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.problems.PackageManager;
import mimuw.idlearn.problems.ProblemPackage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class BlockIOTest {

    private static void preparePlatform() {
        try {
            Platform.startup(() -> {
            });
        }
        catch (IllegalStateException e) {
            // Toolkit already initialized
        }
    }

    @Test
    public void testIncorrectInput() {
        preparePlatform();
        try {
            ProblemPackage pkg = PackageManager.getProblemPackage("Addition");
            pkg.prepareTest(123);

            Group root = new Group();
            CodeBox codeBox = new CodeBox();

            root.getChildren().add(codeBox);

            Read r1 = new Read(pkg);
            Read r2 = new Read(pkg);
            Read r3 = new Read(pkg);
            r1.setText("x");
            r2.setText("x");
            r3.setText("x");

            codeBox.addChild(0, r1);
            codeBox.addChild(0, r2);
            codeBox.addChild(0, r3);

            try {
                Expression<Void> program = codeBox.compile();
                Scope scope = new Scope();
                program.evaluate(scope);
                fail();
            }
            catch (Exception e) {}
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testAddition() {
        preparePlatform();
        try {
            ProblemPackage pkg = PackageManager.getProblemPackage("Addition");
            pkg.prepareTest(123);

            Group root = new Group();
            CodeBox codeBox = new CodeBox();

            root.getChildren().add(codeBox);
            Read r1 = new Read(pkg);
            Read r2 = new Read(pkg);
            r1.setText("x");
            r2.setText("y");

            Operation add = new Operation();
            add.setType("+");
            add.setText("x", "x", "y");

            Write w = new Write(pkg);
            w.setText("x");

            codeBox.addChild(0, w);
            codeBox.addChild(0, add);
            codeBox.addChild(0, r2);
            codeBox.addChild(0, r1);

            Expression<Void> program = codeBox.compile();
            Scope scope = new Scope();
            program.evaluate(scope);
            assertTrue(pkg.checkTest());
        }
        catch (Exception e) {
            fail();
        }
    }
}