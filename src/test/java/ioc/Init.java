package ioc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Init {

	public static Properties getProperties() {
		Properties properties = new Properties();

		File file = new File("animal.properties");

		try {
			if (file.exists()) {
				properties.load(new FileInputStream(file));

			} else {
				properties.setProperty("cat", "ioc.Cat");
				properties.setProperty("snake", "ioc.Snake");
				properties.setProperty("pig", "ioc.Pig");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
}
