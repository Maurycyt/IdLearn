module mimuw.idlearn.userdata {
	requires mimuw.idlearn.core;
	requires org.yaml.snakeyaml;
    requires mimuw.idlearn.packages;

    opens mimuw.idlearn.userdata to org.yaml.snakeyaml;

	exports mimuw.idlearn.userdata;
}