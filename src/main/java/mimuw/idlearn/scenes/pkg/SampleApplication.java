package mimuw.idlearn.scenes.pkg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mimuw.idlearn.scenes.MainMenu;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class SampleApplication extends Application {

    private final SceneController sc = new SceneController();

    @Override
    public void start(Stage stage) throws IOException {
        // Set the title
        stage.setTitle("IdLearn");
        // Set the stage size to the entire screen
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());
        // Set the app icon
        Image icon = new Image(Objects.requireNonNull(getClass().getResource("icon.png")).toString());
        stage.getIcons().add(icon);

/*        Group root = new Group();
        Scene scene = new Scene(root);
        scene.setFill(Color.LIGHTBLUE);*/

/*        Text text = new Text();
        text.setText("sample text :p");
        text.setX(50);
        text.setY(50);
        text.setFont(Font.font("Verdana", 50));
        root.getChildren().add(text);*/

/*        Image logo = new Image(Objects.requireNonNull(getClass().getResource("logo.png")).toString());
        ImageView logoView = new ImageView(logo);
        logoView.setX(400);
        logoView.setY(400);
        root.getChildren().add(logoView);*/

        sc.loadInitialScene("MainMenu.fxml", stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}