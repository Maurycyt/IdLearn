package mimuw.idlearn;

import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mimuw.idlearn.idlang.base.Expression;
import mimuw.idlearn.idlang_gui.CodeBox;
import mimuw.idlearn.idlang_gui.codeblock.CodeBlockSpawner;
import mimuw.idlearn.idlang_gui.codeblock.blocktypes.*;
import mimuw.idlearn.problem_package_system.PackageManager;
import mimuw.idlearn.problem_package_system.ProblemPackage;
import mimuw.idlearn.scenes.SceneManager;
import mimuw.idlearn.scenes.SceneUtils;
import mimuw.idlearn.scenes.preloader.LoadTask;

import java.io.IOException;

public class Application extends javafx.application.Application {
	private final SceneManager sceneManager = SceneManager.getInstance();
	private final int framesPerSecond = 60;

	private static Parent createAdditionTask() {
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

		statement.setTranslateX(100);
		statement.setPrefWidth(700);

		final Button button = new Button("Convert");
		//getChildren().add(button);

		button.setOnMousePressed(event -> {
			Expression<Void> exp = codeBox.compile();
			TestRunner testRunner = new TestRunner(pkg, exp);

			try {
				double meanTime = testRunner.aggregateTestTimes();
				System.out.println("Correct output");
				System.out.println("Time: " + meanTime);
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
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
		//getChildren().addAll(statement, codeBlocks, codeBox, dragged);

		return null;
	}

/*		@Override
		public void handleEvent(Event event) {
			if(event instanceof KeyEvent e) {
				if (e.getEventType().equals(KeyEvent.KEY_PRESSED)) {
					if (e.getCode() == KeyCode.ESCAPE)
						getSceneManager().add(new Pause(getSceneManager()));
				}
			}
		}*/

	@Override
	public void start(Stage stage) throws IOException {
		// Start measuring time of preloading
		long loadStart = System.currentTimeMillis();

		// Set the title
		stage.setTitle("IdLearn");

		// Set the stage size to the entire screen
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		stage.setWidth(screenBounds.getWidth());
		stage.setHeight(screenBounds.getHeight());

		// Set the app icon
		Image icon = new Image(SceneUtils.AppIcon.toExternalForm());
		stage.getIcons().add(icon);

		sceneManager.setStage(stage);
		stage.setScene(new Scene(new Group()));

		// Add the main menu scene with preloading
		sceneManager.push(SceneUtils.loadScene(SceneUtils.MainMenu), new LoadTask() {
			@Override
			public void load() {
				try {
					PackageManager.reloadProblemPackages();
				} catch (RuntimeException e) {
					System.out.println("Package directory altered. Reloading packages...");
					PackageManager.reloadProblemPackageDirectory(true);
					PackageManager.reloadProblemPackages();
				}
				ProblemPackage[] packages = PackageManager.getProblemPackages();
				int i = 0, n = packages.length;
				for (ProblemPackage p : packages) {
					System.out.println(p.getTitle());
					p.build();
					logProgress(i / (float)n);
					i++;
				}

				logSuccess();
			}
		});

		stage.show();

		final Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);

		long loadEnd = System.currentTimeMillis();
		System.out.println("Loaded app in " + (loadEnd - loadStart) + "ms.");
		timeline.play();
	}

	public static void main(String[] args) {
		try {
			launch(args);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
