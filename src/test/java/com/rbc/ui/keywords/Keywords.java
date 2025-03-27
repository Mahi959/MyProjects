package com.rbc.ui.keywords;

import com.rbc.ui.testBase.TestBase;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Keywords extends TestBase {

    public static JavascriptExecutor js = (JavascriptExecutor) driver;
    public static WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    public static void highlightElement(WebElement elementLocator){

        String originalStyle = elementLocator.getAttribute("style");
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", elementLocator);
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); } //short delay
        js.executeScript("arguments[0].setAttribute('style', arguments[0].getAttribute('data-original-style') || '" + originalStyle + "');", elementLocator);
        js.executeScript("arguments[0].removeAttribute('data-original-style');", elementLocator);

    }

    public static void scrollToElement(WebElement locator){

        js.executeScript("arguments[0].scrollIntoView(true);",locator);
    }

    public static void clearAndType(WebElement locator, String textToEnter){

        wait.until(ExpectedConditions.visibilityOf(locator));
        highlightElement(locator);

        // Clear the text
        js.executeScript("arguments[0].value = '';", locator);

        // enter the text
        js.executeScript("arguments[0].value = '"+ textToEnter + "';", locator);

    }


}
