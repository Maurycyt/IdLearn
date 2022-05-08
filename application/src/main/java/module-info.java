module mimuw.idlearn.application {
	requires javafx.controls;
	requires javafx.fxml;
	requires mimuw.idlearn.core;
	requires mimuw.idlearn.idlang;
	requires mimuw.idlearn.packages;
	requires mimuw.idlearn.scoring;
	requires mimuw.idlearn.scenes;
	requires mimuw.idlearn.userdata;

	opens mimuw.idlearn to javafx.fxml;

	exports mimuw.idlearn;
}