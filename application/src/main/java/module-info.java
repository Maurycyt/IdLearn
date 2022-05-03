module mimuw.idlearn.application {
	requires javafx.controls;
	requires javafx.fxml;
	requires mimuw.idlearn.core;
	requires mimuw.idlearn.idlang;
    requires mimuw.idlearn.problem_package_system;
	requires mimuw.idlearn.scenes;

    opens mimuw.idlearn to javafx.fxml;

	exports mimuw.idlearn;
}