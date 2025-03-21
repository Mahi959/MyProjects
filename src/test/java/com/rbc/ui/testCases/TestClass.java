package com.rbc.ui.testCases;

import com.rbc.ui.testBase.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class TestClass extends TestBase {


    @Test(groups = {"dynamicGroup","sanity"})
    public void testMethod() {


        System.out.println("This is a test method");

    }

    @Test(groups = {"dynamicGroup1"})
    public void testMethod1(){
        System.out.println("This is a test method 1");
    }

}
