package com.rbc.ui.locators;

import com.rbc.ui.keywords.Keywords;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class CheckBoxesPage extends BasePage {

    public CheckBoxesPage(WebDriver driver){
        super(driver);
    }

    JavascriptExecutor jse = (JavascriptExecutor) driver;

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @FindBy(xpath = "//h3[text()='Checkboxes']")
    WebElement headerCheckBoxes;

    @FindBy (xpath = "//form[@id='checkboxes'] //input")
    List<WebElement> checkBoxes;

    public boolean isHeaderCorrect(){

        Keywords keywords = new Keywords(driver);
        boolean result = false;
        keywords.highlightElement(headerCheckBoxes);
        wait.until(ExpectedConditions.visibilityOf(headerCheckBoxes));
        if(headerCheckBoxes.getText().equals("Checkboxes")){
            result = true;
        }
        return result;
    }

    public void checkUnchecked(){
        for(WebElement checkBox : checkBoxes){
           boolean isChecked = (boolean) jse.executeScript("return arguments[0].checked;",checkBox);
           if (isChecked == true){
               checkBox.click();
           } else {
               checkBox.click();
           }
        }

    }



}
