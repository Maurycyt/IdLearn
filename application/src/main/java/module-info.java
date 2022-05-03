module mimuw.idlearn.application {
	requires javafx.controls;
	requires javafx.fxml;
	requires mimuw.idlearn.core;
	requires mimuw.idlearn.idlang;
	requires mimuw.idlearn.packages;
	requires mimuw.idlearn.scenes;

	opens mimuw.idlearn to javafx.fxml;

	exports mimuw.idlearn;
	exports mimuw.idlearn.testRunner;
	opens mimuw.idlearn.testRunner to javafx.fxml;
}