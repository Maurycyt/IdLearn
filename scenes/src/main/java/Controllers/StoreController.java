package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import mimuw.idlearn.packages.PackageManager;
import mimuw.idlearn.packages.ProblemPackage.Config.Difficulty;
import mimuw.idlearn.packages.ProblemPackage;
import mimuw.idlearn.userdata.DataManager;
import mimuw.idlearn.userdata.NotEnoughPointsException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class StoreController extends GenericController {
    private Random rand = new Random();

    /**
     * Return a random task of the provided difficulty that the user hasn't yet unlocked, if such a task exists.
     * Otherwise, output `Empty`.
     * @param difficulty : difficulty of the task
     * @return : a random task or no task,
     */
    private Optional<ProblemPackage> getRandomTaskOfDifficulty(Difficulty difficulty) {
        Map<String, ProblemPackage> allTasks = PackageManager.getProblemPackages();
        Map<String, ProblemPackage> userTasks = DataManager.getUnlockedTasks();

        var availableTasks =
                allTasks
                .entrySet()
                .stream()
                .filter(entry -> /*entry.getValue().getDifficulty() == difficulty && */!userTasks.containsKey(entry.getKey())) //todo: uncomment
                .map(Map.Entry::getValue)
                .collect(Collectors.toCollection(ArrayList::new));

        if (availableTasks.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(availableTasks.get(rand.nextInt(availableTasks.size())));
    }

    private void buyTask(Difficulty difficulty) throws IOException {
        Optional<ProblemPackage> task = getRandomTaskOfDifficulty(difficulty);
        if (task.isEmpty()) {
            System.out.println("No " + difficulty + " tasks available");
        }
        else {
            try {
                DataManager.payPoints(/*task.getCost()*/ 1); //todo: uncomment
            } catch (NotEnoughPointsException e) {
                System.out.println("Not enough points!");
                return;
            }
            System.out.println("Bought task: " + task.get().getTitle());
            DataManager.unlockTask(task.get().getTitle());
        }
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
