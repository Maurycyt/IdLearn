package mimuw.idlearn.scenes.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import mimuw.idlearn.packages.PackageManager;
import mimuw.idlearn.packages.ProblemPackage.Config.Difficulty;
import mimuw.idlearn.packages.ProblemPackage;
import mimuw.idlearn.scenes.ResourceHandler;
import mimuw.idlearn.userdata.DataManager;
import mimuw.idlearn.userdata.NotEnoughPointsException;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class TaskStoreController extends GenericController {
    private static final Random rand = new Random(); // for random task selection

    @FXML
    private BorderPane mainBorderPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Button pointsBtn = ResourceHandler.createUserPointsButton();
        mainBorderPane.setTop(pointsBtn);
    }

    /**
     * Return a random task of the provided difficulty that the user hasn't yet unlocked, if such a task exists.
     * Otherwise, output `Empty`.
     * @param difficulty : difficulty of the task
     * @return : a random task or no task,
     */
    private Optional<ProblemPackage> getRandomTaskOfDifficulty(Difficulty difficulty) {
        Map<String, ProblemPackage> allTasks = PackageManager.getProblemPackages();
        List<String> userTasks = DataManager.getUnlockedTasks();

        var availableTasks =
                allTasks
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().getDifficulty() == difficulty && !userTasks.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toCollection(ArrayList::new));

        if (availableTasks.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(availableTasks.get(rand.nextInt(availableTasks.size())));
    }

    private void buyTask(Difficulty difficulty) throws IOException {
        Optional<ProblemPackage> task = getRandomTaskOfDifficulty(difficulty);
        Alert alert;
        if (task.isEmpty()) {
            alert = ResourceHandler.createAlert(Alert.AlertType.WARNING,
                    "No " + difficulty + " tasks available", ButtonType.OK
            );
        } else {
            try {
                DataManager.payPoints(100); //todo: replace with actual cost
                DataManager.unlockTask(task.get().getTitle());

                alert = ResourceHandler.createAlert(Alert.AlertType.INFORMATION,
                        "Acquired task \"" + task.get().getTitle() + "\"", ButtonType.OK
                );
                alert.setHeaderText("Success!");
            } catch (NotEnoughPointsException e) {
                alert = ResourceHandler.createAlert(Alert.AlertType.WARNING,
                        "Gather more points and try again", ButtonType.OK
                );
                alert.setHeaderText("Not enough points!");
            }
        }
        alert.show();
    }

    @FXML
    public void buyEasyTask(ActionEvent event) throws IOException {
        buyTask(Difficulty.Easy);
    }
    @FXML
    public void buyMediumTask(ActionEvent event) throws IOException {
        buyTask(Difficulty.Medium);
    }
    @FXML
    public void buyHardTask(ActionEvent event) throws IOException {
        buyTask(Difficulty.Hard);
    }
}
