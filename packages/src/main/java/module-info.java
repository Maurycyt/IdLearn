module mimuw.idlearn.packages {
	requires org.yaml.snakeyaml;
	requires mimuw.idlearn.core;

	opens mimuw.idlearn.packages to org.yaml.snakeyaml;

	exports mimuw.idlearn.packages;
}