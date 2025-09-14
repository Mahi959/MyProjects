package com.rbc.ui.testCases;

import com.rbc.DriverManager;
import com.rbc.ui.locators.CheckBoxesPage;
import com.rbc.ui.locators.DragAndDrop;
import com.rbc.ui.locators.SwiggyPage;
import com.rbc.ui.testBase.TestBase;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestJavascriptActions extends TestBase {

    SwiggyPage wp;
    CheckBoxesPage cp;
    DragAndDrop dd;
//    @Test(groups = {"Mahesh"})
//    public void findBrokenLinks() throws IOException {
//        // find the broken links
//        wp = new WelcomePage(driver);
//        wp.findBrokenLinks();
//        System.out.println("broken method completed");
//    }

    @Test(groups = {"Mahesh"})
    public void javaScriptActions() {
//        wp = new WelcomePage(driver);
        cp = new CheckBoxesPage(DriverManager.getDriver());

        JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriver();

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

    @Test(groups = {"Mahesh"})
    public void testDragAndDrop() throws InterruptedException {
        wp = new SwiggyPage(DriverManager.getDriver());
        dd = new DragAndDrop(DriverManager.getDriver());
        wp.clickDragAndDrop();
        dd.dragAndDropUsingJS();
        Thread.sleep(2000);
    }

}
