package com.rbc.ui.testCases;

import com.rbc.ui.locators.CheckBoxesPage;
import com.rbc.ui.locators.WelcomePage;
import com.rbc.ui.testBase.TestBase;

import junit.framework.Assert;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import java.io.IOException;


public class TestJavascriptActions extends TestBase {

    WelcomePage wp;
    CheckBoxesPage cp;

    @Test(groups = {"Mahesh"})
    public void findBrokenLinks() throws IOException {
        // find the broken links
        wp = new WelcomePage(driver);
        wp.findBrokenLinks();
        System.out.println("broken method completed");
    }

    @Test(groups = {"Mahesh"})
    public void javaScriptActions() {
        wp = new WelcomePage(driver);
        cp = new CheckBoxesPage(driver);

        JavascriptExecutor jse = (JavascriptExecutor) driver;

        // get the title of the page
        String title = (String) jse.executeScript("return document.title;");
        Assert.assertEquals("The Internet", title);

        // refresh the page using
        jse.executeScript("window.location.reload();");

        // enter input
        wp.clickForgotLink();
        wp.enterEmailInput();

        System.out.println("javaScriptActions method completed.");
    }


}
