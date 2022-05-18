package mimuw.idlearn.scenes.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
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

	private void questionGoingForward(String taskTitle) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
				"Do you wish to continue? Additional points will not be granted",
				ButtonType.YES, ButtonType.CANCEL
		);
		ResourceHandler.addStylesheetToAlert(alert);
		alert.setHeaderText("You've already completed this task");
		alert.showAndWait()
				.filter(response -> response == ButtonType.YES)
				.ifPresent(response -> {
					try {
						enterNextScene(ResourceHandler.Task, new TaskController(taskTitle));
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
	}


	/**
	 * Makes the button of a completed task darker and gives it a popup on click.
	 * This assumes the task's text has been set.
	 * @param taskBtn: button of a task
	 */
	private void alterButtonForCompletedTask(Button taskBtn) {
		taskBtn.setStyle("-fx-background-color: #038c53; -fx-text-fill: lightgrey;");
		taskBtn.setOnAction(event -> questionGoingForward(taskBtn.getText()));
	}

	/** Loads all the user's available tasks as clickable buttons **/
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		Set<String> completedTasks = PointsGiver.getCompletedTasks();
		Button pointsBtn = ResourceHandler.createUserPointsButton();
		mainBorderPane.setTop(pointsBtn);

		List<String> tasks = new java.util.ArrayList<>(DataManager.getUnlockedTasks());
		Collections.sort(tasks);

		double btnWidth = tasksVBox.getMaxWidth();
		for (final String taskTitle : tasks) {
			Button taskBtn = new Button(taskTitle);
			taskBtn.setMaxWidth(btnWidth);
			tasksVBox.getChildren().add(taskBtn);

			// make the style change dynamically
			PointsGiver.connectToTaskCompletion(event -> {
				if (event.value() == taskTitle)
					alterButtonForCompletedTask(taskBtn);
			});

			if (completedTasks.contains(taskTitle)) {
				alterButtonForCompletedTask(taskBtn);
			} else {
				taskBtn.setOnAction((event) -> {
					try {
						enterNextScene(ResourceHandler.Task, new TaskController(taskTitle));
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		}
	}
}



