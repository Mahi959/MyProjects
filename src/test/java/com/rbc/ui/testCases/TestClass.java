package com.rbc.ui.testCases;

import com.rbc.ui.testBase.TestBase;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class TestClass extends TestBase {

    @Test(groups = {"dynamicGroup","sanity"})
    public void testMethod(){
        driver.get("https://www.google.com/");
        System.out.println("This is a test method");

    }

    @Test(groups = {"dynamicGroup1"})
    public void testMethod1(){
        System.out.println("This is a test method 1");
    }

}
