package com.rbc.ui.testCases;


import com.rbc.ui.locators.CheckBoxesPage;
import com.rbc.ui.locators.DragAndDrop;
import com.rbc.ui.locators.WelcomePage;
import com.rbc.ui.testBase.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.DriverManager;

import java.io.IOException;

public class TestActionClass extends TestBase {

    WelcomePage  wp;
    CheckBoxesPage cp;
    DragAndDrop dd;
    @Test(groups = {"Mahesh"})
    public void testCheckBoxes() throws InterruptedException, IOException {

        wp = new WelcomePage(DriverManager.getDriver());
        cp = new CheckBoxesPage(DriverManager.getDriver());
        System.out.println("checkbox method start");
        Thread.sleep(3000);
        wp.clickLinkCheckboxes();
        System.out.println("checkbox method completed");
        Assert.assertTrue(cp.isHeaderCorrect());
        cp.checkUnchecked();
        Thread.sleep(2000);
//        AppUtil.getScreenshot(driver, n);
    }

}
