module mimuw.idlearn.idlang {
	requires javafx.controls;
	requires javafx.fxml;
	requires mimuw.idlearn.packages;

	exports mimuw.idlearn.idlang.logic.base;
	exports mimuw.idlearn.idlang.logic.environment;
	exports mimuw.idlearn.idlang.logic.exceptions;
	exports mimuw.idlearn.idlang.logic.keywords;
	exports mimuw.idlearn.idlang.logic.operators;

	exports mimuw.idlearn.idlang.GUI;
	exports mimuw.idlearn.idlang.GUI.parser;
	exports mimuw.idlearn.idlang.GUI.codeblocks;
	exports mimuw.idlearn.idlang.GUI.codeblocks.blocktypes;
}