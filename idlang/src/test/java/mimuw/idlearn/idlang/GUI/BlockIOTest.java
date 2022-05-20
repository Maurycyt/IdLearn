package mimuw.idlearn.idlang.GUI;

import javafx.application.Platform;
import javafx.scene.Group;
import mimuw.idlearn.idlang.GUI.CodeBox;
import mimuw.idlearn.idlang.GUI.codeblocks.blocktypes.Operation;
import mimuw.idlearn.idlang.GUI.codeblocks.blocktypes.Read;
import mimuw.idlearn.idlang.GUI.codeblocks.blocktypes.Write;
import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.TimeCounter;
import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.packages.PackageManager;
import mimuw.idlearn.packages.ProblemPackage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class BlockIOTest {

	private static void preparePlatform() {
		try {
			Platform.startup(() -> {
			});
		} catch (IllegalStateException e) {
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
			r1.setEffectiveText("x");
			r2.setEffectiveText("x");
			r3.setEffectiveText("x");

			codeBox.addChild(0, r1);
			codeBox.addChild(0, r2);
			codeBox.addChild(0, r3);

			try {
				Expression<Void> program = codeBox.compile();
				Scope scope = new Scope();
				program.evaluate(scope, new TimeCounter(), pkg.getTestInputScanner(123), pkg.getTestOutputWriter(123));
				fail();
			} catch (Exception e) {
			}
		} catch (Exception e) {
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
			r1.setEffectiveText("x");
			r2.setEffectiveText("y");

			Operation add = new Operation();
			add.setType("+");
			add.setEffectiveText("x", "x", "y");

			Write w = new Write(pkg);
			w.setEffectiveText("x");

			codeBox.addChild(0, w);
			codeBox.addChild(0, add);
			codeBox.addChild(0, r2);
			codeBox.addChild(0, r1);

			Expression<Void> program = codeBox.compile();
			Scope scope = new Scope();
			program.evaluate(scope, new TimeCounter(), pkg.getTestInputScanner(123), pkg.getTestOutputWriter(123));
			assertTrue(pkg.checkTest(123));
		} catch (Exception e) {
			fail();
		}
	}
}