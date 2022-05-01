module mimuw.idlearn {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.yaml.snakeyaml;


	opens mimuw.idlearn to javafx.fxml;
	opens mimuw.idlearn.problems to org.yaml.snakeyaml;


	exports mimuw.idlearn;
	exports mimuw.idlearn.core;
	exports mimuw.idlearn.scenes;
	opens mimuw.idlearn.scenes to javafx.fxml;
	exports mimuw.idlearn.scenes.preloader;
	opens mimuw.idlearn.scenes.preloader to javafx.fxml;
}