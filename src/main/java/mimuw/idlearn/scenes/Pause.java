package mimuw.idlearn.scenes;

import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class Pause extends Scene {
	public Pause(SceneManager sceneManager) {
		super(sceneManager);

		Label label = new Label("Pause");

		Button resume = new Button("Resume");
		resume.setOnAction(e -> getSceneManager().pop());
		resume.setLayoutY(50);

		Button restart = new Button("Restart");
		restart.setOnAction(e -> {
			getSceneManager().pop(); // pos self
			getSceneManager().replace(new Play(getSceneManager())); // replace play with new play
		});
		restart.setLayoutY(100);

		Button settings = new Button("Settings");
		settings.setOnAction(e -> getSceneManager().add(new Settings(getSceneManager())));
		settings.setLayoutY(150);

		Button mainMenu = new Button("Main menu");
		mainMenu.setOnAction(e -> {
			getSceneManager().pop(); // pos self
			getSceneManager().replace(new MainMenu(getSceneManager())); // replace play with main menu
		});
		mainMenu.setLayoutY(200);

		getChildren().addAll(label, resume, restart, settings, mainMenu);

		setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ESCAPE)
				sceneManager.pop();
		});
	}

	@Override
	public void handleEvent(Event event) {

	}

	@Override
	public void update(Duration time) {

	}
}
