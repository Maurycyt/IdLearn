module mimuw.idlearn.problem_package_system {
	requires org.yaml.snakeyaml;
	requires mimuw.idlearn.core;

	opens mimuw.idlearn.problem_package_system to org.yaml.snakeyaml;

	exports mimuw.idlearn.problem_package_system;
}