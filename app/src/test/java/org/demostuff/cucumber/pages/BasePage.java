package org.demostuff.cucumber.pages;

import static org.junit.Assert.assertTrue;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by dragan.kocovski on 30/05/2017.
 */
public abstract class BasePage {

    private WebDriver driver;

    protected BasePage (WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
        assertTrue(isLoaded());
    }

    public WebDriver getDriver() {
        return driver;
    }

    abstract boolean isLoaded();

    protected void wait(int timeOutInSeconds) {
        try {
            Thread.sleep(timeOutInSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    protected boolean sendKeysWithoutTab(WebElement webElement, String dataKey, Map<String, String> dataTableMap) {
//        boolean ret = false;
//        String value = dataTableMap.get(dataKey);
//        if (value != null) {
//            // Clear the element if it contains text
//            if (!StringUtils.isEmpty(webElement.getText())) {
//                webElement.clear();
//            }
//            webElement.sendKeys(SpelUtils.dataTableSubstitions(getDriver(), value));
//            ret = true;
//        }
//        return ret;
//    }

    /**
     * By default send the TAB char so that we tab off the field and trigger any field ui validations
     * @param webElement
     * @param dataKey
     * @param dataTableMap
     */
//    protected void sendKeys(WebElement webElement, String dataKey, Map<String, String> dataTableMap) {
//        if (sendKeysWithoutTab(webElement, dataKey, dataTableMap)) {
//            webElement.sendKeys(Keys.TAB);
//        }
//    }

    protected void selectListBoxValue(WebElement webElement, String dataKey, Map<String, String> dataTableMap) {
        String value = dataTableMap.get(dataKey);
        if (value != null) {
            Select dateDropDown = new Select(webElement);
            dateDropDown.selectByVisibleText(value);
        }
    }
}
