package com.rbc.ui.testCases;


import com.rbc.ui.locators.CheckBoxesPage;
import com.rbc.ui.locators.DragAndDrop;
import com.rbc.ui.locators.SwiggyPage;
import com.rbc.ui.testBase.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.DriverManager;
import util.FileUtil;

import java.io.IOException;

public class TestActionClass extends TestBase {

    SwiggyPage sp;
    CheckBoxesPage cp;
    DragAndDrop dd;
    @Test(groups = {"Mahesh1"})
    public void testCheckBoxes() throws InterruptedException, IOException {

        sp = new SwiggyPage(DriverManager.getDriver());
        cp = new CheckBoxesPage(DriverManager.getDriver());
        System.out.println("checkbox method start");
        Thread.sleep(3000);
        sp.clickLinkCheckboxes();
        System.out.println("checkbox method completed");
        Assert.assertTrue(cp.isHeaderCorrect());
        cp.checkUnchecked();
        Thread.sleep(2000);
//        AppUtil.getScreenshot(driver, n);
    }

    @Test(groups = {"Mahesh"})
    public void downloadReportsFromSwiggy() throws InterruptedException {
        sp = new SwiggyPage(DriverManager.getDriver());
        DriverManager.getDriver().get(FileUtil.getPropValue("Swiggy_URL"));
        DriverManager.getDriver().manage().window().maximize();
        sp.loginToSwiggy();
        sp.switchToDelivery();
//        boolean isNavigatedToFinance = sp.navigateToFinance();
//        Assert.assertTrue(isNavigatedToFinance);
        sp.navigateToFinance();
        sp.downloadReports();



    }

}
