module mimuw.idlearn.scenes {
	requires javafx.controls;
	requires javafx.fxml;
	requires mimuw.idlearn.core;

	exports mimuw.idlearn.scenes;
	opens mimuw.idlearn.scenes to javafx.fxml;
	exports mimuw.idlearn.scenes.preloader;
	opens mimuw.idlearn.scenes.preloader to javafx.fxml;
}