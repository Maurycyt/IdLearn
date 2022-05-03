package mimuw.idlearn.idlang_gui;

import javafx.application.Platform;
import mimuw.idlearn.idlang_gui.codeblock.CodeBlock;
import mimuw.idlearn.idlang_gui.codeblock.CodeSegment;
import mimuw.idlearn.idlang.base.Expression;
import mimuw.idlearn.idlang.base.TimeCounter;
import mimuw.idlearn.idlang.environment.Scope;
import mimuw.idlearn.idlang.exceptions.SimulationException;
import mimuw.idlearn.idlang_gui.codeblock.blocktypes.*;
import mimuw.idlearn.problem_package_system.PackageManager;
import mimuw.idlearn.problem_package_system.ProblemPackage;
import mimuw.idlearn.TestRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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

	@Test
	public void testSlowAddition() {
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
		whilePositive.setText("y");

		Operation add1x = new Operation();
		add1x.setType("+");
		add1x.setText("x", "x", "1");
		whilePositive.addChild(0, add1x);

		Operation sub1y = new Operation();
		sub1y.setType("-");
		sub1y.setText("y", "y", "1");
		whilePositive.addChild(0, sub1y);

		WhileBlock whileNegative = new WhileBlock();
		whileNegative.setText("y");

		Operation sub1x = new Operation();
		sub1x.setType("-");
		sub1x.setText("x", "x", "1");
		whileNegative.addChild(0, sub1x);

		Operation add1y = new Operation();
		add1y.setType("+");
		add1y.setText("y", "1", "y");
		whileNegative.addChild(0, add1y);

		IfElse ifElse = new IfElse();
		ifElse.setText("z");
		ifElse.addChild(2*CodeBlock.HEIGHT, whileNegative);
		ifElse.addChild(0, whilePositive);

		CodeSegment segment = new CodeSegment();

		Write writex = new Write(pkg);
		writex.setText("x");
		segment.addChild(0, writex);

		segment.addChild(0, ifElse);

		Operation compare = new Operation();
		compare.setType(">");
		compare.setText("z", "y", "0");
		segment.addChild(0, compare);

		Read readx = new Read(pkg);
		readx.setText("x");

		Read ready = new Read(pkg);
		ready.setText("y");

		segment.addChild(0, ready);
		segment.addChild(0, readx);

		Expression<Void> exp = segment.convert();

		TestRunner testRunner = new TestRunner(pkg, exp);
		try {
			testRunner.aggregateTestTimes();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}
}
