package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetDbConnProperties extends GetPropertiesAbstract {
	
	String[] properties = {"", "", ""};
	private InputStream inputStream;
	
	public String[] getPropValues() throws IOException {
 
		try {
			Properties prop = new Properties();
			String propFileName = "dbconfig.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("Property file '" + propFileName + "' not found in the classpath");
			}
 
			//Get the property value
			String dbUrl = prop.getProperty("DB_URL");
			String user = prop.getProperty("USER");
			String pw = prop.getProperty("PW");
			
			properties[0] = dbUrl;
			properties[1] = user;
			properties[2] = pw;
			
			
		} catch (Exception e) {
			//TODO Handle exception
		} finally {
			inputStream.close();
		}
		return properties;
	}
}
