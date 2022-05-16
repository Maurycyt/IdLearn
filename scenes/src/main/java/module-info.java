module mimuw.idlearn.scenes {
	requires javafx.controls;
	requires javafx.fxml;
	requires mimuw.idlearn.core;
    requires mimuw.idlearn.idlang;
	requires mimuw.idlearn.scoring;
	requires mimuw.idlearn.packages;
    requires mimuw.idlearn.userdata;

    exports mimuw.idlearn.scenes;
	opens mimuw.idlearn.scenes to javafx.fxml;
	exports mimuw.idlearn.scenes.preloading;
	opens mimuw.idlearn.scenes.preloading to javafx.fxml;
    exports mimuw.idlearn.scenes.controllers;
    opens mimuw.idlearn.scenes.controllers to javafx.fxml;
}