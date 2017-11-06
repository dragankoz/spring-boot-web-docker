package org.demostuff.utilities;

import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvUtils {
    private  static final Logger LOG = LoggerFactory.getLogger(EnvUtils.class);

    public static final String DEFAULT_SELENIUM_ENV = getDefaultSeleniumEnv();

    public static final String SELENIUM_APP_URL = "selenium.app.url";

    private static Properties envProperties = null;

    private static String environmentFilename = String.format("env-%s.properties", System.getProperty("env", DEFAULT_SELENIUM_ENV));

    private static String getDefaultSeleniumEnv() {
        String ret = null;
        for (String k: System.getProperties().stringPropertyNames()) {
            if (k.startsWith("idea.test")) {
                ret = "developer";
                break;
            }
        }
        return ret != null ? ret : System.getProperty("selenium.env","maven");
    }

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