module mimuw.idlearn.application {
	requires javafx.graphics;
	requires javafx.base;
	requires mimuw.idlearn.packages;
	requires mimuw.idlearn.scoring;
	requires mimuw.idlearn.scenes;
	requires mimuw.idlearn.userdata;
	requires mimuw.idlearn.idlangblocks;
	requires mimuw.idlearn.achievements;

	opens mimuw.idlearn to javafx.fxml;

	exports mimuw.idlearn;
}