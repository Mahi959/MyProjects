package com.rbc.ui.locators;

import com.rbc.ui.keywords.Keywords;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckBoxesPage extends BasePage {

    public CheckBoxesPage(WebDriver driver){
        super(driver);
    }

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @FindBy(xpath = "//h3[text()='Checkboxes']")
    WebElement headerCheckBoxes;

    @FindBy ()
    WebElement checkboxOne;

    public boolean isHeaderCorrect(){

        boolean result = false;
        Keywords.highlightElement(headerCheckBoxes);
        wait.until(ExpectedConditions.visibilityOf(headerCheckBoxes));
        if(headerCheckBoxes.getText().equals("Checkboxes")){
            result = true;
        }
        return result;
    }



}
