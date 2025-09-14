package keywords;

import net.bytebuddy.implementation.bytecode.Throw;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.time.Duration;
import java.util.List;

public class Keywords {

    public WebDriver driver;

    public Keywords(WebDriver driver) {
        this.driver = driver;
    }

    public void highlightElement(WebElement elementLocator) {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        String originalStyle = elementLocator.getAttribute("style");
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", elementLocator);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } //short delay
        js.executeScript("arguments[0].setAttribute('style', arguments[0].getAttribute('data-original-style') || '" + originalStyle + "');", elementLocator);
        js.executeScript("arguments[0].removeAttribute('data-original-style');", elementLocator);

    }

    public void scrollToElement(WebElement locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOf(locator));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        actions.moveToElement(locator).perform();

        js.executeScript("arguments[0].scrollIntoView(true);", locator);
    }

    public void clearAndType(WebElement locator, String textToEnter) {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(locator));
        highlightElement(locator);

        // Clear the text
        js.executeScript("arguments[0].value = '';", locator);

        // enter the text
        js.executeScript("arguments[0].value = '" + textToEnter + "';", locator);

    }

    public void clickElement(WebElement locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOf(locator));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollToElement(locator);
        highlightElement(locator);

        locator.click();
    }

    public boolean isDisplayedAndEnabled(WebElement element) {

        boolean result;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(element));
            boolean isDisplayed = element.isDisplayed();
            boolean isEnabled = element.isEnabled();
            wait.until(ExpectedConditions.elementToBeClickable(element));
            result = (isDisplayed && isEnabled);
            return result;

        } catch (Exception e) {
            return false;
        }
    }

    public String getAttributeValue(WebElement wElement){
        String titleValue = "";
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOf(wElement));
             titleValue = wElement.getDomAttribute("title");

            
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return  titleValue;
        
    }

    public String getText(WebElement wElement){
        String text = "";
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOf(wElement));
            highlightElement(wElement);
            text = wElement.getText();
            return text;

        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void scrollTo(List<WebElement> wElements,String scrollToElement){
        List<WebElement> elements = wElements;

        for( WebElement element : elements){
            scrollToElement(element);
            highlightElement(element);
            if(element.getText().equals(scrollToElement)){
                clickElement(element);
                break;
            }
        }

    }

    public void jClick(WebElement wElement){

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click;", wElement);

    }


    public String getInnerText(WebElement wElement){

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String str =  (String) js.executeScript("return arguments[0].innerText;", wElement);
        return str;

    }

    public String getTextContent(WebElement wElement){

        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript("return arguments[0].textContent;", wElement);

    }

    public String getInnerHTML(WebElement wElement){

        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript("return arguments[0].innerHTML;", wElement);

    }

}
