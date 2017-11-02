package org.demostuff.utilities;

import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvUtils {
    private  static final Logger LOG = LoggerFactory.getLogger(EnvUtils.class);

    public static final String DEFAULT_ENV = System.getProperty("selenium.env","developer");

    public static final String APP_URL = "app.url";

    private static Properties envProperties = null;

    private static String environmentFilename = String.format("env-%s.properties", System.getProperty("env", DEFAULT_ENV));

    public static String getEnvProperty(String key) {
        String ret = null;
        if (key != null) {
            if (envProperties == null) {
                try {
                    URL propertiesFile = EnvUtils.class.getClassLoader().getResource(environmentFilename);
                    if (propertiesFile != null) {
                        LOG.info("Reading from propertiesFile: [{}]", propertiesFile.toExternalForm());
                        Properties p = new Properties();
                        p.load(propertiesFile.openStream());
                        envProperties = p;
                    } else {
                        throw new Exception(String.format("Properties file missing: [%s]", environmentFilename));
                    }
                } catch (Exception ex) {
                    LOG.error("Could not read properties file", ex);
                    throw new IllegalStateException(ex);
                }
            }
            ret = envProperties.get(key) != null ? String.valueOf(envProperties.get(key)) : System.getProperty(key);
        }
        LOG.info("getEnvProperty({})={}",key,ret);
        return ret;
    }
}