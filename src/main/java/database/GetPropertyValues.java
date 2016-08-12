package database;

import processor.Processor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetPropertyValues {
    InputStream inputStream;
    String url;
    Properties prop = new Properties();
    public Logger logger = Logger.getLogger("GetPropertyValues");

    public Properties getPropValues() throws IOException {

        try {
            String propFileName = "databaseConfig.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            url = prop.getProperty("url");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during getting properties values", e);
        } finally {
            inputStream.close();
        }
        return prop;
    }
}
