package com.rbc.ui.testCases;

import com.rbc.ui.locators.CheckBoxesPage;
import com.rbc.ui.locators.WelcomePage;
import com.rbc.ui.testBase.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestClass1 extends TestBase {

    WelcomePage wp;
    CheckBoxesPage cp;

    @Test(groups = {"Mahesh"})
    public void testCheckBoxes() {

        wp = new WelcomePage(driver);
        cp = new CheckBoxesPage(driver);

        wp.clickLinkCheckboxes();
        Assert.assertTrue(cp.isHeaderCorrect());
    }
}
