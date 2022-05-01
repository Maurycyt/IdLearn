package mimuw.idlearn.scenes;

import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import mimuw.idlearn.GUI.coding.CodeBox;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlockSpawner;
import mimuw.idlearn.GUI.coding.codeblock.blocktypes.*;
import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.base.TimeCounter;
import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.language.exceptions.SimulationException;
import mimuw.idlearn.problems.PackageManager;
import mimuw.idlearn.problems.ProblemPackage;

public class Play extends Scene {
	public Play(SceneManager sceneManager) {
		super(sceneManager);

		// Create base elements
		final CodeBox codeBox = new CodeBox();
		final Pane codeBlocks = new VBox();
		final Group dragged = new Group();

		// Set positions
		codeBlocks.setTranslateX(50);
		codeBlocks.setTranslateY(200);

		codeBox.setTranslateX(300);
		codeBox.setTranslateY(200);

		//codeBox.setPrefSize(300, 400);
		final ProblemPackage pkg;
		ProblemPackage tmpPkg = null;
		try {
			tmpPkg = PackageManager.getProblemPackage("Addition");
		} catch (Exception e) {
			e.printStackTrace();
		}

		pkg = tmpPkg;
		assert pkg != null;
		final TextArea statement = new TextArea(pkg.getStatement());
		statement.setWrapText(true);
		pkg.prepareTest(123);

		statement.setTranslateX(100);
		statement.setPrefWidth(700);

		final Button button = new Button("Convert");
		getChildren().add(button);

		button.setOnMousePressed(event -> {
			Expression<Void> exp = codeBox.compile();
			Scope scope = new Scope();
			try {
				pkg.resetIO();
			} catch (Exception e) {
				e.printStackTrace();
			}
			TimeCounter counter = new TimeCounter();
			try {
				exp.evaluate(scope, counter);
			} catch (SimulationException e) {
				e.printStackTrace();
			}
			if (pkg.checkTest()) {
				System.out.println("Correct output");
				System.out.println("Time: " + counter.getTime());
			} else {
				System.out.println("Incorrect output");
			}
		});

		// Create spawners
		Node readSpawner = new CodeBlockSpawner(codeBox, dragged, () -> new Read(pkg));
		Node writeSpawner = new CodeBlockSpawner(codeBox, dragged, () -> new Write(pkg));
		Node assignSpawner = new CodeBlockSpawner(codeBox, dragged, Assign::new);
		Node operationSpawner = new CodeBlockSpawner(codeBox, dragged, Operation::new);
		Node whileSpawner = new CodeBlockSpawner(codeBox, dragged, WhileBlock::new);
		Node ifSpawner = new CodeBlockSpawner(codeBox, dragged, IfElse::new);

		// Link spawners
		codeBlocks.getChildren().addAll(readSpawner, writeSpawner, assignSpawner, operationSpawner, whileSpawner, ifSpawner);

		// Link everything else
		getChildren().addAll(statement, codeBlocks, codeBox, dragged);
	}

	@Override
	public void handleEvent(Event event) {
		if(event instanceof KeyEvent e) {
			if (e.getEventType().equals(KeyEvent.KEY_PRESSED)) {
				if (e.getCode() == KeyCode.ESCAPE)
					getSceneManager().add(new Pause(getSceneManager()));
			}
		}
	}

	@Override
	public void update(Duration time) {

	}
}
