package mimuw.idlearn.scenes;

import Controllers.TaskSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import mimuw.idlearn.idlang.GUI.CodeBox;
import mimuw.idlearn.idlang.GUI.codeblocks.CodeBlockSpawner;
import mimuw.idlearn.idlang.GUI.codeblocks.blocktypes.*;
import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.exceptions.TimeoutException;
import mimuw.idlearn.packages.PackageManager;
import mimuw.idlearn.packages.ProblemPackage;
import mimuw.idlearn.scenes.preloader.LoadTask;
import mimuw.idlearn.scenes.preloader.Preloader;
import mimuw.idlearn.scoring.PointsGiver;
import mimuw.idlearn.scoring.TestRunner;
import mimuw.idlearn.scoring.WrongAnswerException;

import java.io.IOException;
import java.net.URL;

/**
 * A set of utility functions related to scenes
 */
public class SceneUtils {
	public static URL MainMenu = SceneUtils.class.getResource("scenes/MainMenu.fxml");
	public static URL GameMenu = SceneUtils.class.getResource("scenes/GameMenu.fxml");
	public static URL Preloader = SceneUtils.class.getResource("scenes/Preloader.fxml");
	public static URL Settings = SceneUtils.class.getResource("scenes/Settings.fxml");
	public static URL Achievements = SceneUtils.class.getResource("scenes/Achievements.fxml");
	public static URL Store = SceneUtils.class.getResource("scenes/Store.fxml");
	public static URL TaskSelection = SceneUtils.class.getResource("scenes/TaskSelection.fxml");
	public static URL Task = SceneUtils.class.getResource("scenes/Task.fxml");

	//public static URL AdditionTask = SceneUtils.class.getResource("scenes/AdditionTask.fxml");

	public static URL StyleSheet = SceneUtils.class.getResource("style.css");
	public static URL AppIcon = SceneUtils.class.getResource("images/icon.png");
	public static URL AppLogo = SceneUtils.class.getResource("images/logo.png");

	/**
	 * Load a scene from an .fxml file and return its root
	 * @param url url of the file
	 * @return root of loaded scene
	 * @throws IOException thrown when loading of the file fails
	 */
	public static Parent loadScene(URL url) throws IOException {
		Scene scene = new Scene(FXMLLoader.load(url));
		Parent root = scene.getRoot();
		root.getStylesheets().add(StyleSheet.toExternalForm());
		scene.setRoot(new Group());
		return root;
	}
	public static Parent loadScene(FXMLLoader loader) throws IOException {
		Scene scene = new Scene(loader.load());
		Parent root = scene.getRoot();
		root.getStylesheets().add(StyleSheet.toExternalForm());
		scene.setRoot(new Group());
		return root;
	}

	public static Parent createPreloader(LoadTask task) {
		try {
			BorderPane root = (BorderPane)loadScene(SceneUtils.Preloader);
			root.getChildren().add(new Preloader(task));
			return root;
		} catch (IOException e) {
			e.printStackTrace();
			return new Preloader(task);
		}
	}

	public static Scene createAdditionTaskScene() {
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
			try {
				TestRunner runner = new TestRunner(pkg, exp);
				double time = runner.aggregateTestTimes();
				System.out.println("Correct output!");
				System.out.println("Time: " + time);
				PointsGiver.setSolutionSpeed(pkg.getTitle(), (long) (time * 1000), 10);
			} catch (WrongAnswerException e) {
				System.out.println("Incorrect output!");
			} catch (TimeoutException e) {
				System.out.println("Time out!");
			} catch (Exception e) {
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
		codeBlocks.getChildren().add(readSpawner);
		codeBlocks.getChildren().add(writeSpawner);
		codeBlocks.getChildren().add(assignSpawner);
		codeBlocks.getChildren().add(operationSpawner);
		codeBlocks.getChildren().add(whileSpawner);
		codeBlocks.getChildren().add(ifSpawner);

		// Link everything else
		root.getChildren().add(statement);
		root.getChildren().add(codeBlocks);
		root.getChildren().add(codeBox);
		root.getChildren().add(dragged);

		return new Scene(root, 1400, 800);
	}
}
