package mimuw.idlearn.GUI.coding;

import javafx.application.Platform;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlock;
import mimuw.idlearn.GUI.coding.codeblock.CodeSegment;
import mimuw.idlearn.GUI.coding.codeblock.blocktypes.*;
import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.TimeCounter;
import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.language.exceptions.SimulationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompilerTest {

	private static void preparePlatform() {
		try {
			Platform.startup(() -> {
			});
		} catch (IllegalStateException e) {
			// Toolkit already initialized
		}
	}

	@Test
	public void testBasicCompile() throws SimulationException {
		preparePlatform();
		CodeSegment segment = new CodeSegment();

		Assign assignx = new Assign();
		assignx.setText("x", "1");

		Assign assigny = new Assign();
		assigny.setText("y", "10");

		WhileBlock whileB = new WhileBlock();
		whileB.setText("y");

		Operation sub = new Operation();
		sub.setType("-");
		sub.setText("y", "y", "1");

		Operation mul = new Operation();
		mul.setType("×");
		mul.setText("x", "2", "x");

		whileB.addChild(CodeBlock.HEIGHT, mul);
		whileB.addChild(CodeBlock.HEIGHT, sub);
		segment.addChild(0, whileB);
		segment.addChild(0, assigny);
		segment.addChild(0, assignx);

		Expression<Void> exp = segment.convert();
		Scope scope = new Scope();
		exp.evaluate(scope, new TimeCounter());
		assertEquals((int) Math.pow(2, 10), scope.getVariable("x").getValue());
	}
}
