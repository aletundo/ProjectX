package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetWorkhoursProperties extends GetPropertiesAbstract {
	private static final GetWorkhoursProperties INSTANCE = new GetWorkhoursProperties();
	
	public GetWorkhoursProperties(){
		
	}
	
	public static GetWorkhoursProperties getInstance(){
		return INSTANCE;
	}
	
	
	public Integer[] getPropValues() throws IOException {
		
		Integer[] properties = new Integer[3];
		InputStream inputStream = null;
 
		try {
			Properties prop = new Properties();
			String propFileName = "workhours.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("Property file '" + propFileName + "' not found in the classpath");
			}
 
			//Get the property value
			int hourPerDay = Integer.parseInt(prop.getProperty("HOURPERDAY"));
			int projectHourPerDay = Integer.parseInt(prop.getProperty("PROJECTHOURPERDAY"));
			int stageHourPerDay = Integer.parseInt(prop.getProperty("STAGEHOURPERDAY"));
			
			properties[0] = hourPerDay;
			properties[1] = projectHourPerDay;
			properties[2] = stageHourPerDay;
			
			
		} catch (Exception e) {
			//TODO Handle exception
		} finally {
			inputStream.close();
		}
		return properties;
	}
}
