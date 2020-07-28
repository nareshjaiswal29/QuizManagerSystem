package fr.epita.quiz.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
	
	Properties properties = new Properties();
	private static Configuration configuration;
	
	private Configuration(){
		try {
			properties.load(new FileInputStream(new File("conf.properties")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getConfValue(String key) {
		String property = this.properties.getProperty(key);
		if (property == null) {
			return "";
		}
		return property;
	}

	public static Configuration getInstance() {
		if (configuration == null) {
			configuration = new Configuration();
		}
		return configuration;
	}

}
