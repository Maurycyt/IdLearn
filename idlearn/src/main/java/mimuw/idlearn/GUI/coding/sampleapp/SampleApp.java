package mimuw.idlearn.GUI.coding.sampleapp;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mimuw.idlearn.GUI.coding.CodeBox;
import mimuw.idlearn.GUI.coding.codeblock.blocktypes.*;
import mimuw.idlearn.GUI.coding.codeblock.CodeBlockSpawner;
import mimuw.idlearn.language.base.Expression;
import mimuw.idlearn.language.environment.Scope;
import mimuw.idlearn.problems.PackageManager;
import mimuw.idlearn.problems.ProblemPackage;

public class SampleApp extends Application {


	public static void main(final String[] args) {
			launch(args);
	}

	@Override
	public void start(final Stage stage) {

		// Create base elements
		final Group root = new Group();
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
		root.getChildren().add(button);

		button.setOnMousePressed(event -> {
			Expression<Void> exp = codeBox.compile();
			Scope scope = new Scope();
			try {
				pkg.resetIO();
			} catch (Exception e) {
				e.printStackTrace();
			}
			exp.evaluate(scope);
			if (pkg.checkTest()) {
				System.out.println("Correct output");
			}
				else {
				System.out.println("Incorrect output");
			}
		});

		// Create spawners
		Node readSpawner = new CodeBlockSpawner(codeBox, dragged, () -> new Read(pkg));
		Node writeSpawner = new CodeBlockSpawner(codeBox, dragged, () -> new Write(pkg));
		Node assignSpawner = new CodeBlockSpawner(codeBox, dragged, Assign::new);
		Node addSpawner = new CodeBlockSpawner(codeBox, dragged, Add::new);
		Node subSpawner = new CodeBlockSpawner(codeBox, dragged, Subtract::new);
		Node mulSpawner = new CodeBlockSpawner(codeBox, dragged, Multiply::new);
		Node whileSpawner = new CodeBlockSpawner(codeBox, dragged, WhileBlock::new);

		// Link spawners
		codeBlocks.getChildren().add(readSpawner);
		codeBlocks.getChildren().add(writeSpawner);
		codeBlocks.getChildren().add(assignSpawner);
		codeBlocks.getChildren().add(addSpawner);
		codeBlocks.getChildren().add(subSpawner);
		codeBlocks.getChildren().add(mulSpawner);
		codeBlocks.getChildren().add(whileSpawner);

		// Link everything else
		root.getChildren().add(statement);
		root.getChildren().add(codeBlocks);
		root.getChildren().add(codeBox);
		root.getChildren().add(dragged);

		final Scene scene = new Scene(root, 800, 600);
		stage.setScene(scene);
		stage.show();
	}
}
