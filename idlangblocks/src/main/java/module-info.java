module mimuw.idlearn.idlangblocks {
	requires javafx.controls;
	requires javafx.fxml;
	requires mimuw.idlearn.packages;
	requires mimuw.idlearn.idlang;
	requires mimuw.idlearn.userdata;

	exports mimuw.idlearn.idlangblocks.GUI;
	exports mimuw.idlearn.idlangblocks.GUI.parser;
	exports mimuw.idlearn.idlangblocks.GUI.codeblocks;
	exports mimuw.idlearn.idlangblocks.GUI.codeblocks.blocktypes;
}