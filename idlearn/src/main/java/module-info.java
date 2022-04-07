module mimuw.idlearn {
	requires javafx.controls;
	requires javafx.fxml;
	
	
	opens mimuw.idlearn to javafx.fxml;
	exports mimuw.idlearn;
	exports mimuw.idlearn.core;
	exports mimuw.idlearn.scenes;
	opens mimuw.idlearn.scenes to javafx.fxml;
}