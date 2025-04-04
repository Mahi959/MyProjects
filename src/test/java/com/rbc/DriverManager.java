package com.rbc;
import org.openqa.selenium.WebDriver;
import java.util.Objects;

public class DriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }

    public static void quitDriver() {
        if (Objects.nonNull(driver.get())) {
            driver.get().quit();
            driver.remove();
        }
    }
}
