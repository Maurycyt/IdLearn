package mimuw.idlearn;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mimuw.idlearn.problems.PackageManager;
import mimuw.idlearn.problems.ProblemPackage;

import java.io.IOException;

public class HelloApplication extends Application{
	@Override
	public void start(Stage stage) throws IOException{
		ProblemPackage [] packages = PackageManager.getProblemPackages();
		for (ProblemPackage p : packages) {
			System.out.println(p.getTitle());
		}

		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 320, 240);
		stage.setTitle("Hello!");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args){
		launch();
	}
}