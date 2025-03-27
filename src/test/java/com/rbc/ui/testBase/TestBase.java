package com.rbc.ui.testBase;

import com.rbc.util.FileUtil;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;

public class TestBase {

    public WebDriver driver;

    @BeforeMethod(groups = {"sanity", "regression", "dryRun","Mahesh"})
    public void setUp() throws IOException {

        String browserName = System.getProperty("browser", "chrome");
        String tags = System.getProperty("tags", "dynamicGroup");
        String browserHeadLess = System.getProperty("browser.headless.mode", "false");
        String env = System.getProperty("env","pte");
        String useSeleniumGrid = System.getProperty("useSeleniumGrid", "N");

        System.out.println("browserName : " + browserName);
        System.out.println("tags : " + tags);
        System.out.println("useSelenium Grid : " + useSeleniumGrid);
        System.out.println("browser headless : " + browserHeadLess);
        System.out.println("env : " + env);

        if (useSeleniumGrid.equals("Y")) {
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            if (FileUtil.getPropValue("os").equalsIgnoreCase("windows")) {
                desiredCapabilities.setPlatform(Platform.WINDOWS);
            } else if (FileUtil.getPropValue("os").equalsIgnoreCase("mac")) {
                desiredCapabilities.setPlatform(Platform.MAC);
            } else {
                System.out.println("No matching OS found!!!");
            }

            switch (browserName.toLowerCase()) {
                case "chrome":
                    desiredCapabilities.setBrowserName("chrome");
                    break;
                case "edge":
                    desiredCapabilities.setBrowserName("MicrosoftEdge");
                    break;
                default:
                    System.out.println("No Matching Browser found!!!");
            }
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), desiredCapabilities);

        } else if (useSeleniumGrid.equals("N")) {

            switch (browserName.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    if (browserHeadLess.equalsIgnoreCase("true")) {
                        chromeOptions.addArguments("--headless");
                    }
                    driver = new ChromeDriver(chromeOptions);
                    break;

                case "msedge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
                    edgeOptions.addArguments("--remote-allow-origins=*");
                    if (browserHeadLess.equalsIgnoreCase("true")) {
                        edgeOptions.addArguments("--headless");
                        System.out.println("inside headless");
                    }
                    driver = new EdgeDriver(edgeOptions);
                    break;
                default:
                    System.out.println("No matching browser found!!!");
                    break;
            }
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://the-internet.herokuapp.com/");
        wait.until(ExpectedConditions.urlToBe("https://the-internet.herokuapp.com/"));
        driver.manage().window().maximize();
//        driver.manage().deleteAllCookies();
    }

//    public WebDriver getDriver(){
//        return this.driver;
//    }

    @AfterMethod(groups = {"sanity", "regression", "dryRun","Mahesh"})
    public void tearDown() {
        driver.quit();
    }
}
