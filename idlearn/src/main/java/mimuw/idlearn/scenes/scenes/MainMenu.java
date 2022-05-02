package mimuw.idlearn.scenes;

import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class MainMenu extends Scene {
	public MainMenu(SceneManager sceneManager) {
		super(sceneManager);

		Label label = new Label("IdLearn");

		Button resume = new Button("Play");
		resume.setOnAction(e -> getSceneManager().replace(new Play(getSceneManager())));
		resume.setLayoutY(50);

		Button settings = new Button("Settings");
		settings.setOnAction(e -> getSceneManager().add(new Settings(getSceneManager())));
		settings.setLayoutY(100);

		Button mainMenu = new Button("Exit");
		mainMenu.setOnAction(e -> exit());
		mainMenu.setLayoutY(150);

		getChildren().addAll(label, resume, settings, mainMenu);
	}

	@Override
	public void handleEvent(Event event) {

	}

	@Override
	public void update(Duration time) {

	}
}
