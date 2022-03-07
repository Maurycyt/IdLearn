module mimuw.idlearn {
	requires javafx.controls;
	requires javafx.fxml;
	
	
	opens mimuw.idlearn to javafx.fxml;
	exports mimuw.idlearn;
}