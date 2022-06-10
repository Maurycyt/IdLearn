module mimuw.idlearn.userdata {
	requires mimuw.idlearn.core;
	requires mimuw.idlearn.properties;
	requires org.yaml.snakeyaml;
    requires mimuw.idlearn.packages;
	requires mimuw.idlearn.idlang;

	opens mimuw.idlearn.userdata to org.yaml.snakeyaml;

	exports mimuw.idlearn.userdata;
}