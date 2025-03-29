package com.rbc.ui.locators;

import com.rbc.ui.keywords.Keywords;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.*;

public class WelcomePage extends BasePage {

    public WelcomePage(WebDriver driver) {
        super(driver);
    }

    Keywords keywords = new Keywords(driver);

    WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));

    @FindBy (tagName = "A")
    List<WebElement> allLinks;

    @FindBy (xpath="//a[text()='Checkboxes']")
    WebElement linkCheckboxes;

    @FindBy (linkText = "Forgot Password")
    WebElement linkForgotPassword;

    @FindBy (name = "email")
    WebElement inputEmail;

    @FindBy (linkText = "Drag and Drop")
     WebElement linkDragAndDrop;

//    Keywords keywords = new Keywords(driver);

    public void clickLinkCheckboxes() {

        wait.until(ExpectedConditions.elementToBeClickable(linkCheckboxes));
        keywords.scrollToElement(linkCheckboxes);
        System.out.println("click checkbox");
        wait.until(ExpectedConditions.elementToBeClickable(linkCheckboxes));
        keywords.highlightElement(linkCheckboxes);
        linkCheckboxes.click();
    }

    public void clickForgotLink(){
//        keywords.scrollToElement(linkForgotPassword);
        wait.until(ExpectedConditions.elementToBeClickable(linkForgotPassword));
//        keywords.highlightElement(linkForgotPassword);
        linkForgotPassword.click();
        System.out.println("After click frogto");
    }

    public void enterEmailInput(){
        System.out.println("Before enter");
        keywords.clearAndType(inputEmail,"Mahesh");
        System.out.println("After enter");
    }

    public void findBrokenLinks() throws IOException {
        String url ="";
        for (WebElement links : allLinks ){
           url = links.getAttribute("href");

            if(url == null || url.isEmpty()) {
                System.out.println(url + " does not contains a proper link.");
                continue;
            }
            HttpURLConnection httpsURLConnection = (HttpURLConnection)(new URL(url)).openConnection();
            httpsURLConnection.connect();

            if (httpsURLConnection.getResponseCode()>=400){
                System.out.println(url + " is broken.");
            }
            else {
                System.out.println(url + " is a valid url.");
            }

        }
    }

    public void clickDragAndDrop(){
        keywords.clickElement(linkDragAndDrop);
    }



}
