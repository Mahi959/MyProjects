package com.rbc.ui.locators;

import keywords.Keywords;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class DragAndDrop extends BasePage{

    public DragAndDrop(WebDriver driver){
        super(driver);
    }

    Keywords keywords = new Keywords(driver);
    Actions actions = new Actions(driver);

    @FindBy (id = "column-a")
    WebElement source;

    @FindBy (id = "column-b")
    WebElement destination;

    public void dragAndDropUsingJS() {
        keywords.highlightElement(source);
        String script = "var source = arguments[0], destination = arguments[1];"
                + "var dataTransfer = new DataTransfer();"
                + "source.dispatchEvent(new DragEvent('dragstart', { dataTransfer }));"
                + "destination.dispatchEvent(new DragEvent('drop', { dataTransfer }));"
                + "source.dispatchEvent(new DragEvent('dragend', { dataTransfer }));";
        ((JavascriptExecutor) driver).executeScript(script, source, destination);
    }


}
