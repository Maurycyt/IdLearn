package mimuw.idlearn.idlang.GUI;

import javafx.application.Platform;
import mimuw.idlearn.idlang.GUI.codeblocks.CodeBlock;
import mimuw.idlearn.idlang.GUI.codeblocks.CodeSegment;
import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.base.TimeCounter;
import mimuw.idlearn.idlang.logic.environment.Scope;
import mimuw.idlearn.idlang.logic.exceptions.SimulationException;
import mimuw.idlearn.idlang.GUI.codeblocks.blocktypes.*;
import mimuw.idlearn.packages.PackageManager;
import mimuw.idlearn.packages.ProblemPackage;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

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
		assignx.setEffectiveText("x", "1");

		Assign assigny = new Assign();
		assigny.setEffectiveText("y", "10");

		WhileBlock whileB = new WhileBlock();
		whileB.setEffectiveText("y");

		Operation sub = new Operation();
		sub.setType("-");
		sub.setEffectiveText("y", "y", "1");

		Operation mul = new Operation();
		mul.setType("×");
		mul.setEffectiveText("x", "2", "x");

		whileB.addChild(CodeBlock.HEIGHT, mul);
		whileB.addChild(CodeBlock.HEIGHT, sub);
		segment.addChild(0, whileB);
		segment.addChild(0, assigny);
		segment.addChild(0, assignx);

		Expression exp = segment.convert();
		Scope scope = new Scope();
		exp.evaluate(scope, new TimeCounter(), null, null);
		assertEquals((int) Math.pow(2, 10), scope.getVariable("x").value);
	}

	@Test
	public void testSlowAddition() throws IOException {
		preparePlatform();

		final ProblemPackage pkg;
		ProblemPackage tmpPkg = null;
		try {
			tmpPkg = PackageManager.getProblemPackage("Addition");
		} catch (Exception e) {
			fail();
		}

		pkg = tmpPkg;

		WhileBlock whilePositive = new WhileBlock();
		whilePositive.setEffectiveText("y");

		Operation add1x = new Operation();
		add1x.setType("+");
		add1x.setEffectiveText("x", "x", "1");
		whilePositive.addChild(0, add1x);

		Operation sub1y = new Operation();
		sub1y.setType("-");
		sub1y.setEffectiveText("y", "y", "1");
		whilePositive.addChild(0, sub1y);

		WhileBlock whileNegative = new WhileBlock();
		whileNegative.setEffectiveText("y");

		Operation sub1x = new Operation();
		sub1x.setType("-");
		sub1x.setEffectiveText("x", "x", "1");
		whileNegative.addChild(0, sub1x);

		Operation add1y = new Operation();
		add1y.setType("+");
		add1y.setEffectiveText("y", "1", "y");
		whileNegative.addChild(0, add1y);

		IfElse ifElse = new IfElse();
		ifElse.setEffectiveText("z");
		ifElse.addChild(2*CodeBlock.HEIGHT, whileNegative);
		ifElse.addChild(0, whilePositive);

		CodeSegment segment = new CodeSegment();

		Write writex = new Write(pkg);
		writex.setEffectiveText("x");
		segment.addChild(0, writex);

		segment.addChild(0, ifElse);

		Operation compare = new Operation();
		compare.setType(">");
		compare.setEffectiveText("z", "y", "0");
		segment.addChild(0, compare);

		Read readx = new Read(pkg);
		readx.setEffectiveText("x");

		Read ready = new Read(pkg);
		ready.setEffectiveText("y");

		segment.addChild(0, ready);
		segment.addChild(0, readx);

		Expression exp = segment.convert();

		pkg.prepareTest(123);

		try {
			exp.evaluate(new Scope(), new TimeCounter(), pkg.getTestInputScanner(123), pkg.getTestOutputWriter(123));
		} catch (SimulationException e) {
			e.printStackTrace();
			fail();
		}

		assertTrue(pkg.checkTest(123));
	}
}
