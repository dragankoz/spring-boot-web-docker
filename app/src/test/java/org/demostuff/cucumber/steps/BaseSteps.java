package org.demostuff.cucumber.steps;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cucumber.api.Scenario;

public class BaseSteps {
    private static final Logger LOG = LoggerFactory.getLogger(BaseSteps.class);

    private static String OS = System.getProperty("os.name").toLowerCase();

    public static final String IGNORE_TAG = "@ignore";

    private WebDriver driver;

    protected WebDriver startWebDriver() {
        LOG.info("startWebDriver()");
        driver = createChromeWebDriver();
        settingImplicitWait(driver);
        return driver;
    }

    protected void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    protected WebDriver getDriver() {
        return driver;
    }

    protected void stopWebDriver(Scenario scenario) {
        LOG.info("stopWebDriver()");
        LOG.info("Waiting for page to finish loading before screenshotting...");
        wait(3);
        final byte[] screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
        scenario.embed(screenshot, "image/png"); //stick it in the report
        driver.close();
        driver.quit();
    }

    protected void wait(int timeOutInSeconds) {
        try {
            Thread.sleep(timeOutInSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String getModuleBaseDir() {
        String baseDir = System.getProperty("user.dir");

        Class thisClass = BaseSteps.class;
        String classResourceName = thisClass.getName().replace(BaseSteps.class.getSimpleName(), "").replace(".", "/");
        classResourceName = classResourceName + thisClass.getClass().getSimpleName() + ".class";
        URL classResourcePathURL = thisClass.getClassLoader().getResource(classResourceName);
        if (classResourcePathURL != null) {
            int splitIndex = classResourcePathURL.getPath().indexOf("/target/classes");
            baseDir = classResourcePathURL.getPath().substring(0, splitIndex);
        }
        return baseDir;
    }

    private static WebDriver createChromeWebDriver() {
        // Use "target" as our temp dir since firefox profiles can fill up our usual user temp dir
        System.setProperty("java.io.tmpdir", getModuleBaseDir() + "/target");

        WebDriver driver = null;
        ChromeOptions chromeOptions = new ChromeOptions();
        // Are we running against a docker-selenium
        int dockerSeleniumServicePort = -1;
        try {
            LOG.info("docker.selenium.service.port={}", System.getProperty("docker.selenium.service.port"));
            dockerSeleniumServicePort = Integer.parseInt(System.getProperty("docker.selenium.service.port"));
            driver = new RemoteWebDriver(new URL("http://localhost:" + dockerSeleniumServicePort + "/wd/hub"), DesiredCapabilities.chrome());
        } catch (NumberFormatException nfe) {
            LOG.info("Ahh not using docker-selenium");
        } catch (Exception ex) {
            LOG.error("Error initializing remote driver", ex);
        }

        if (dockerSeleniumServicePort < 0) {
            if (OS.contains("windows")) {
                System.setProperty("webdriver.chrome.driver", System.getProperty("chrome.binaries.basedir") + "/chromedriver.exe");
                // By default we use desktop installed chrome
                // Use chromium executable
                //chromeOptions.setBinary(getModuleBaseDir() + "/binaries/chrome-win32/chrome.exe");
            } else if (OS.contains("linux")) {
                System.setProperty("webdriver.chrome.driver", System.getProperty("chrome.binaries.basedir") + "/chromedriver");
                chromeOptions.addArguments("--no-sandbox");
                // By default we use desktop installed chrome
                // Use chromium executable
                //chromeOptions.setBinary(getModuleBaseDir() + "/binaries/chrome-linux/chrome");
            }
            driver = new ChromeDriver(chromeOptions);
            driver.manage().window().maximize();
        }
        return driver;
    }

    private static void settingImplicitWait(WebDriver driver) {
        //setting implicit wait for driver
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

}
