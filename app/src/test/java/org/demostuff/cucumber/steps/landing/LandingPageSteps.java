package org.demostuff.cucumber.steps.landing;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.demostuff.cucumber.pages.LandingPage;
import org.demostuff.cucumber.steps.BaseSteps;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LandingPageSteps extends BaseSteps {
    private static final Logger LOG = LoggerFactory.getLogger(LandingPageSteps.class);

    @Before //(IGNORE_TAG)
    public void startBrowser() {
        startWebDriver();
    }

    @After //(IGNORE_TAG)
    public void closeBrowser(Scenario scenario) {
        stopWebDriver(scenario);
    }

    @Given("^I open a browser$")
    public void assertDriver() {
        assertNotNull(getDriver());
    }

    @When("^I navigate to the landing page$")
    public void landingPage() {
        LandingPage.go(getDriver());
    }

    @Then("^I should see the landing page$")
    public void assertLandingPage() {
        assertTrue(getDriver().findElement(By.xpath(LandingPage.XPATH_HEADER)) != null);
    }
}
