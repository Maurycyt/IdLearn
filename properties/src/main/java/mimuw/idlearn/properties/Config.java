package mimuw.idlearn.properties;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

public class Config {
	private static Path dataPath = null;
	private static final Config instance = new Config();

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
}
