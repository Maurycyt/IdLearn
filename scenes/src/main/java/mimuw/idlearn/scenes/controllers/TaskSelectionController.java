package mimuw.idlearn.scenes.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import mimuw.idlearn.scenes.ResourceHandler;
import mimuw.idlearn.scoring.PointsGiver;
import mimuw.idlearn.userdata.DataManager;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class TaskSelectionController extends GenericController {
	@FXML
	private VBox tasksVBox;
	@FXML
	private BorderPane mainBorderPane;

	private void connectButtonToCoding(Button codeBtn, String taskTitle, boolean isCompleted) {
		// make the style change dynamically
		PointsGiver.connectToTaskCompletion(event -> {
			String incomingText = (String)event.value();
			if (incomingText.equals(codeBtn.getText()))
				ResourceHandler.setStyleForUnlockedAsset(codeBtn);
		});
		if (isCompleted)
			ResourceHandler.setStyleForUnlockedAsset(codeBtn);

		codeBtn.setOnAction((event) -> {
			try {
				CodingController controller = new CodingController(taskTitle);
				enterNextScene(ResourceHandler.Coding, controller);
				controller.setSavingOnExit();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	private void connectButtonToTestSolving(Button testsBtn, String taskTitle) {
		testsBtn.setOnAction((event) -> {
			try {
				TestSolvingController controller = new TestSolvingController(taskTitle);
				enterNextScene(ResourceHandler.TestSolving, controller);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	/** Loads all the user's available tasks as clickable buttons **/
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		Button pointsBtn = ResourceHandler.createUserPointsButton();
		mainBorderPane.setTop(pointsBtn);
		Set<String> completedTasks = PointsGiver.getCompletedTasks();

		List<String> tasks = new java.util.ArrayList<>(DataManager.getUnlockedTasks());
		Collections.sort(tasks);

		for (String taskTitle : tasks) {
			try {
				GridPane gridPane = FXMLLoader.load(ResourceHandler.TaskGridPane);
				gridPane.setStyle("-fx-cursor: hand"); // adding this here as the hand cursor doesn't work in the children for some reason

				var label = (Label)gridPane.getChildren().get(0);
				var codeBtn = (Button)gridPane.getChildren().get(2);
				var testsBtn = (Button)gridPane.getChildren().get(1);

				label.setText(taskTitle);
				label.setStyle("-fx-font-size: 20px");
				label.setWrapText(true);
				connectButtonToCoding(codeBtn, taskTitle, completedTasks.contains(taskTitle));
				connectButtonToTestSolving(testsBtn, taskTitle);

				tasksVBox.getChildren().add(gridPane);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}



