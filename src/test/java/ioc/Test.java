package ioc;

import java.util.Properties;

public class Test {
	
	public static void main(String[] args) {
		Properties properties = Init.getProperties();
		Animal animal = Factory.getInstance(properties.getProperty("cat"));
		animal.eat();
	}
}
