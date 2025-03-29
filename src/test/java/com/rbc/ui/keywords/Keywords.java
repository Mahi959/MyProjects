package com.rbc.ui.keywords;

import com.rbc.ui.locators.BasePage;
import com.rbc.ui.testBase.TestBase;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Keywords {

    public WebDriver driver;

    public Keywords(WebDriver driver) {
        this.driver = driver;
    }

    public void highlightElement(WebElement elementLocator){

         JavascriptExecutor js = (JavascriptExecutor) driver;

        String originalStyle = elementLocator.getAttribute("style");
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", elementLocator);
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); } //short delay
        js.executeScript("arguments[0].setAttribute('style', arguments[0].getAttribute('data-original-style') || '" + originalStyle + "');", elementLocator);
        js.executeScript("arguments[0].removeAttribute('data-original-style');", elementLocator);

    }

    public void scrollToElement(WebElement locator){

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("arguments[0].scrollIntoView(true);",locator);
    }

    public void clearAndType(WebElement locator, String textToEnter){

        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(locator));
        highlightElement(locator);

        // Clear the text
        js.executeScript("arguments[0].value = '';", locator);

        // enter the text
        js.executeScript("arguments[0].value = '"+ textToEnter + "';", locator);

    }

    public void clickElement(WebElement locator){
        scrollToElement(locator);
        highlightElement(locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        locator.click();
    }

}
