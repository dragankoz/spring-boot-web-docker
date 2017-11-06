package org.demostuff.cucumber.utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Created by dragan.kocovski on 1/06/2017.
 */
public class PageUtils {

    public static void clickElement(WebDriver driver, WebElement element) {
        new Actions(driver).moveToElement(element).click().perform();
    }

    public static void scrollToAndClickElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
        new Actions(driver).moveToElement(element).click().perform();
    }


    public static void clickAndHoldElement(WebDriver driver, WebElement element) {
        new Actions(driver).moveToElement(element).clickAndHold().perform();
    }
}
