package mimuw.idlearn.properties;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class Config {
	private static Path dataPath = null;
	private static final Set<String> defaultTaskTitles = new HashSet<>(List.of(
			"Addition",
			"Addition 2 : Electric Boogaloo",
			"Comparison",
			"GCD",
			"Whack-a-mole"
	));

	public static Path getDataPath() {
		if (dataPath == null) {
			dataPath = Path.of(System.getProperty("user.home"), ".idlearn");
			try (InputStream is = Config.class.getResourceAsStream("application.properties")) {
				Properties p = new Properties();
				p.load(is);
				dataPath = Path.of(p.getProperty("data").replaceFirst("^~", System.getProperty("user.home")));
			} catch (IOException e){
				System.err.println("Couldn't load application.properties file, continuing with default values" + e);
			}
		}
		return dataPath;
	}

	public static Set<String> getDefaultTaskTitles() {
		return defaultTaskTitles;
	}
}
