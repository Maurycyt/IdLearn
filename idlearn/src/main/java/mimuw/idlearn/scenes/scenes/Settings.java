package mimuw.idlearn.scenes;

import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

public class Settings extends Scene {
	public Settings(SceneManager sceneManager) {
		super(sceneManager);

		Label label = new Label();
		label.setText("Settings");

		Button back = new Button("Return");
		back.setOnAction(e -> getSceneManager().pop());
		back.setLayoutY(50);

		getChildren().addAll(label, back);
	}

	@Override
	public void handleEvent(Event event) {
		if(event instanceof KeyEvent e){
			if (e.getEventType().equals(KeyEvent.KEY_PRESSED)) {
				if (e.getCode() == KeyCode.ESCAPE)
					getSceneManager().pop();
			}
		}
	}

	@Override
	public void update(Duration time) {

	}
}
