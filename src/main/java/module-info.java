module mimuw.idlearn {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.yaml.snakeyaml;


	opens mimuw.idlearn to javafx.fxml;
	opens mimuw.idlearn.problems to org.yaml.snakeyaml;


	exports mimuw.idlearn;
	exports mimuw.idlearn.core;
	exports mimuw.idlearn.GUI.coding.sampleapp;
}