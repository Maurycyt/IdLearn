package mimuw.idlearn.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	private final String dataPath;
	private static Config instance = new Config();

	public Config() {
		String dataPath = "~/.idlearn";
		try (InputStream is = Config.class.getResourceAsStream("application.properties")) {
			Properties p = new Properties();
			p.load(is);
			dataPath = p.getProperty("data");
		} catch (IOException e){
			System.err.println("Couldn't load application.properties file, continuing with default values" + e);
		}
		this.dataPath = dataPath;
	}

	public String getDataPath() {
		return dataPath;
	}

	public static Config getInstance(){
		return instance;
	}
}
