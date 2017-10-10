package com.travelbross.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyConfiguration {
	
	private static Properties prop;
	
	static {
		InputStream is = null;
		try {
			String filename = "config-test.properties";
			prop = new Properties();
			is = PropertyConfiguration.class.getClassLoader().getResourceAsStream(filename);
			prop.load(is);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getPropertyValue(String key) {
		return prop.getProperty(key);
	}

}
