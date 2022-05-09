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
	exports mimuw.idlearn.scenes.preloader;
	opens mimuw.idlearn.scenes.preloader to javafx.fxml;
    exports Controllers;
    opens Controllers to javafx.fxml;
}