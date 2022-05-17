package mimuw.idlearn.scenes.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mimuw.idlearn.scenes.ResourceHandler;
import mimuw.idlearn.userdata.DataManager;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TaskSelectionController extends GenericController {
	@FXML
	private VBox tasksVBox;
	@FXML
	private BorderPane mainBorderPane;

	/** Loads all the user's available tasks as clickable buttons **/
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		Button pointsBtn = ResourceHandler.createUserPointsButton();
		mainBorderPane.setTop(pointsBtn);

		List<String> tasks = DataManager.getUnlockedTasks();
		double btnWidth = tasksVBox.getMaxWidth();

		for (final String title : tasks) {
			Button taskBtn = new Button(title);
			taskBtn.setMaxWidth(btnWidth);
			taskBtn.setOnMouseClicked((event) -> {
				System.out.println("Selected task \"" + title + "\"");
				try {
					enterNextScene(ResourceHandler.Task, new TaskController(title));
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			tasksVBox.getChildren().add(taskBtn);
		}
	}
}



