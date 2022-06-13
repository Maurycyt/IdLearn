package mimuw.idlearn.scenes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.*;
import javafx.util.Duration;
import mimuw.idlearn.scenes.controllers.GenericController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import mimuw.idlearn.scenes.preloading.LoadTask;
import mimuw.idlearn.scenes.preloading.Preloader;
import mimuw.idlearn.userdata.DataManager;

import java.io.IOException;
import java.net.URL;

/**
 * A set of utility functions related to scenes and other resources
 */
public class ResourceHandler {
	public static URL MainMenu = ResourceHandler.class.getResource("scenes/MainMenu.fxml");
	public static URL GameMenu = ResourceHandler.class.getResource("scenes/GameMenu.fxml");
	public static URL Preloader = ResourceHandler.class.getResource("scenes/Preloader.fxml");
	public static URL Settings = ResourceHandler.class.getResource("scenes/Settings.fxml");
	public static URL Achievements = ResourceHandler.class.getResource("scenes/Achievements.fxml");
	public static URL Store = ResourceHandler.class.getResource("scenes/Store.fxml");
	public static URL TaskStore = ResourceHandler.class.getResource("scenes/TaskStore.fxml");
	public static URL PerkStore = ResourceHandler.class.getResource("scenes/PerkStore.fxml");
	public static URL CosmeticsStore = ResourceHandler.class.getResource("scenes/CosmeticsStore.fxml");
	public static URL TaskSelection = ResourceHandler.class.getResource("scenes/TaskSelection.fxml");
	public static URL Coding = ResourceHandler.class.getResource("scenes/Coding.fxml");
	public static URL TestSolving = ResourceHandler.class.getResource("scenes/TestSolving.fxml");
	public static URL Credits = ResourceHandler.class.getResource("scenes/Credits.fxml");

	public static URL TaskGridPane = ResourceHandler.class.getResource("elements/TaskGridPane.fxml");

	public static URL Style = ResourceHandler.class.getResource("style.css");
	public static URL CommonStyle = ResourceHandler.class.getResource("common_style.css");
	public static URL AppIcon = ResourceHandler.class.getResource("images/icon.png");

	/**
	 * Load a scene from an .fxml file and return its root
	 * @param url url of the file
	 * @return root of loaded scene
	 * @throws IOException thrown when loading of the file fails
	 */
	public static Parent loadScene(URL url) throws IOException {
		return loadScene(url, null);
	}

	/**
	 * Same as above, but sets the scene's controller based on its 2nd argument.
	 * @param url url of the file
	 * @return root of loaded scene
	 * @throws IOException thrown when loading of the file fails
	 */
	public static Parent loadScene(URL url, GenericController controller) throws IOException {
		FXMLLoader loader = new FXMLLoader(url);
		if (controller != null) {
			loader.setController(controller);
		}
		Scene scene = new Scene(loader.load());
		Parent root = scene.getRoot();
		scene.setRoot(new Group());
		return root;
	}

	/**
	 * Creates a preloader for the `task`
	 * @param task some time-consuming task which the user must wait for
	 * @return root of the Preloader scene
	 */
	public static Parent createPreloader(LoadTask task) throws IOException {
		BorderPane root = (BorderPane)loadScene(ResourceHandler.Preloader);
		root.getChildren().add(new Preloader(task));
		return root;
	}

	/**
	 * Creates an unclickable button that appears on most scenes and displays user points.
	 * @return the points button
	 */
	public static Button createUserPointsButton() {
		Button btn = new Button();
		btn.getStylesheets().add(Style.toExternalForm());
		btn.getStyleClass().add("unclickableGreenButton");

		btn.setText("Points: " + DataManager.showPoints());
		// dynamically updates the points
		DataManager.connectToPoints(event -> btn.setText("Points: " + DataManager.showPoints()));

		BorderPane.setMargin(btn, new Insets(40, 0, 0, 0));
		BorderPane.setAlignment(btn, Pos.CENTER);
		return btn;
	}

	/**
	 * Creates an alert and styles it according to its type.
	 * @param alertType: type of alert
	 * @param s: contents of the alert
	 * @param buttonTypes: types of buttons of the alert
	 * @return the alert
	 */
	public static Alert createAlert(Alert.AlertType alertType, String s, ButtonType... buttonTypes) {
		Alert alert = new Alert(alertType, s, buttonTypes);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(Style.toExternalForm());
		dialogPane.getStylesheets().add(CommonStyle.toExternalForm());

		switch (alertType) {
			case INFORMATION -> dialogPane.getStyleClass().add("ok-dialog");
			case CONFIRMATION, WARNING -> dialogPane.getStyleClass().add("warning-dialog");
			case ERROR -> dialogPane.getStyleClass().add("error-dialog");
		}

		Stage stage = (Stage) dialogPane.getScene().getWindow();
		stage.getIcons().add(new Image(AppIcon.toExternalForm()));

		return alert;
	}

	public static void createAchievementAlert(String s){
		Alert popup = new Alert(Alert.AlertType.NONE);
		popup.initModality(Modality.NONE);

		Label label = new Label(s);
		label.setMinWidth(Label.USE_PREF_SIZE);
		label.setAlignment(Pos.CENTER);

		DialogPane dialogPane = popup.getDialogPane();
		dialogPane.getStylesheets().add(Style.toExternalForm());
		dialogPane.getStylesheets().add(CommonStyle.toExternalForm());
		dialogPane.getStyleClass().add("ok-dialog");
		dialogPane.setContent(label);

		Stage stage = (Stage) dialogPane.getScene().getWindow();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.getIcons().add(new Image(AppIcon.toExternalForm()));
		stage.setX((Screen.getPrimary().getBounds().getWidth() - dialogPane.getWidth()) / 2);
		popup.setY(50);


		double delta = 1 / 15.0;
		int[] iter = new int[1];
		int iterCount = 15;
		iter[0] = 1;

		System.out.println("Spawning achievement" + s);
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(
				new Duration(100),
				actionEvent -> {
					stage.setOpacity((iterCount - iter[0]) * delta);
					iter[0]++;
					if(iter[0] == iterCount){
						stage.hide();
						stage.close();
					}
				}
		));
		timeline.setDelay(new Duration(3000));
		timeline.setCycleCount(iterCount);
		timeline.play();

		popup.show();
	}

	/**
	 * Wrapper for creating a clickable green button with styling.
	 * @param s: the button's text
	 * @param maxWidth: the max width of the button
	 * @return the button
	 */
	public static Button createGreenButton(String s, double maxWidth) {
		Button btn = new Button(s);
		btn.setMaxWidth(maxWidth);
		btn.getStylesheets().add(ResourceHandler.Style.toExternalForm());
		btn.getStyleClass().add("greenButton");
		return btn;
	}

	public static BorderPane createAchievementBorderPane(String s) {
		BorderPane pane = new BorderPane();
		pane.getStylesheets().add(Style.toExternalForm());
		pane.getStylesheets().add(CommonStyle.toExternalForm());
		pane.getStyleClass().addAll("pane", "borderedPane", "achievementPane");
		pane.setPrefHeight(75);

		Label label = new Label(s);
		label.setAlignment(Pos.CENTER);
		label.getStyleClass().add("achievementLabel");
		pane.setLeft(label);
		BorderPane.setAlignment(label, Pos.CENTER);

		ProgressBar pBar = new ProgressBar();
		pane.setRight(pBar);
		BorderPane.setAlignment(pBar, Pos.CENTER);

		return pane;
	}

	public static ObservableValue<Double> createBinding(ScrollPane scrollPane, boolean heightBinding, double offset) {
		if (heightBinding) {
			return new ObservableValue<>() {
				@Override
				public void addListener(InvalidationListener invalidationListener) {
					scrollPane.heightProperty().addListener(invalidationListener);
				}
				@Override
				public void removeListener(InvalidationListener invalidationListener) {
					scrollPane.heightProperty().removeListener(invalidationListener);
				}
				@Override
				public void addListener(ChangeListener<? super Double> changeListener) {
					scrollPane.heightProperty().addListener((ChangeListener<? super Number>) changeListener);
				}
				@Override
				public void removeListener(ChangeListener<? super Double> changeListener) {
					scrollPane.heightProperty().removeListener((ChangeListener<? super Number>) changeListener);
				}
				@Override
				public Double getValue() {
					return scrollPane.getHeight() - offset;
				}
			};
		} else {
			return new ObservableValue<>() {
				@Override
				public void addListener(InvalidationListener invalidationListener) {
					scrollPane.widthProperty().addListener(invalidationListener);
				}
				@Override
				public void removeListener(InvalidationListener invalidationListener) {
					scrollPane.widthProperty().removeListener(invalidationListener);
				}
				@Override
				public void addListener(ChangeListener<? super Double> changeListener) {
					scrollPane.widthProperty().addListener((ChangeListener<? super Number>) changeListener);
				}
				@Override
				public void removeListener(ChangeListener<? super Double> changeListener) {
					scrollPane.widthProperty().removeListener((ChangeListener<? super Number>) changeListener);
				}
				@Override
				public Double getValue() {
					return scrollPane.getWidth() - offset;
				}
			};
		}

	}

	/**
	 * Changes the style of a completed/unlocked asset,
	 * so that it's different from the others of its type.
	 * @param asset: node of an asset
	 */
	public static void setStyleForUnlockedAsset(Node asset) {
		asset.getStyleClass().add("unlockedAsset");
	}

	public static void setStyleForFullyUnlockedAchievement(Node asset) {
		asset.getStyleClass().add("fullyUnlockedAchievement");
	}

	public static void setStyleForAchievement(Node asset) {
		asset.getStyleClass().add("achievementText");
	}
}
