package org.demostuff.tests;

import static org.demostuff.CucumberConstants.FEATURES_PREFIX;
import static org.demostuff.CucumberConstants.PACKAGE_PREFIX;
import static org.demostuff.tests.LandingPageIT.FEATURE_NAME;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = FEATURES_PREFIX + "/" + FEATURE_NAME,
        glue = "classpath:" + PACKAGE_PREFIX + "/steps/" + FEATURE_NAME,
        monochrome = true,
        format = {
                "html:target/cucumber/html/" + FEATURE_NAME,
                "json:target/cucumber/json/" + FEATURE_NAME + ".json",
                "pretty"
        },
        tags = {"~@ignore"}
)
public class LandingPageIT {
    static final String FEATURE_NAME = "landing";
}
