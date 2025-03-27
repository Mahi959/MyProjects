package com.rbc.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.io.FileHandler;


public class AppUtil {

    public String randowString()
    {
        String generatedString= RandomStringUtils.randomAlphabetic(5);
        return generatedString;
    }

    public String randomNumber()
    {
        String generatedString=RandomStringUtils.randomAlphanumeric(10);
        return generatedString;
    }

    public String randomAlphaNumeric()
    {
        String str=RandomStringUtils.randomAlphabetic(5);
        String num=RandomStringUtils.randomAlphanumeric(10);
        return str+num;
    }

    public static void getScreenshot(WebDriver driver) throws IOException {
        String timeStamp =new  SimpleDateFormat("dd_MM_yyyy_hhmm").format(new Date());
        TakesScreenshot screen = (TakesScreenshot) driver;
        File screenshot = screen.getScreenshotAs(OutputType.FILE);

        String fileName = timeStamp+".png";

        File destination = new File("src/test/screenshots/"+fileName);
        try {
            FileHandler.copy(screenshot,destination);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
