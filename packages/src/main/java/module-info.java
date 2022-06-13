module mimuw.idlearn.packages {
	requires mimuw.idlearn.properties;
	requires org.yaml.snakeyaml;
	requires mimuw.idlearn.achievements;
	requires mimuw.idlearn.core;

	opens mimuw.idlearn.packages to org.yaml.snakeyaml;

	exports mimuw.idlearn.packages;
}