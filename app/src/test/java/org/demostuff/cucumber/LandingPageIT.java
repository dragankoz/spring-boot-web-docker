package org.demostuff.cucumber;

import static org.demostuff.cucumber.utilities.CucumberConstants.FEATURES_PREFIX;
import static org.demostuff.cucumber.utilities.CucumberConstants.PACKAGE_PREFIX;
import static org.demostuff.cucumber.LandingPageIT.FEATURE_NAME;

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
