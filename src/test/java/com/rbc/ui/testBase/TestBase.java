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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class TestBase {

    public WebDriver driver;

    @BeforeClass(groups = {"sanity", "regression", "dynamicGroup"})
    public void setUp() throws IOException {

        String browserName = System.getProperty("browser", "chrome");
        String tags = System.getProperty("tags", "dynamicGroup");
        String browserHeadLess = System.getProperty("browser.headless.mode", "false");
        String env = System.getProperty("env","pte");
        String useSeleniumGrid = System.getProperty("useSeleniumGrid", "Y");

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

            System.out.println("inside useSeleniumGrid = N ");
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
                    System.out.println("inside edge");
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

    }

    @AfterClass(groups = {"sanity", "regression", "dynamicGroup"})
    public void tearDown() {
        driver.quit();
    }
}
