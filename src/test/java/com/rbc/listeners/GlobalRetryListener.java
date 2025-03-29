package com.rbc.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class GlobalRetryListener implements ITestListener,IAnnotationTransformer {

    public ExtentSparkReporter sparkReporter;  //UI of the report
    public ExtentReports extent;  //Populate common info on the report
    //Creating TC entry in the report and update status of the test methods
    public ExtentTest test ;


    @Override
    public void onTestStart(ITestResult result) {

        try {
            // Step 1: Define the report path and check for null or empty
            String reportPath = "./src/test/reports/myReport.html";  // Use current directory for simplicity
            System.out.println("Report Path: " + reportPath);  // For debugging

            // Step 2: Ensure the directory exists, create if it doesn't
            File reportDir = new File(reportPath).getParentFile();  // Get the parent directory
            if (!reportDir.exists()) {
                if (reportDir.mkdirs()) {
                    System.out.println("Directory created: " + reportDir.getAbsolutePath());  // Debugging log
                } else {
                    System.out.println("Failed to create directory: " + reportDir.getAbsolutePath());
                }
            }

            // Step 3: Initialize the ExtentSparkReporter with the valid path
            if (reportPath != null && !reportPath.isEmpty()) {
                sparkReporter = new ExtentSparkReporter(reportPath);
                System.out.println("ExtentSparkReporter initialized successfully with path: " + reportPath);  // Debugging log
            } else {
                System.out.println("Invalid report path.");
            }

        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);
        // Initialize the ExtentReports instance
        if (extent == null) {
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);


            String computerName = java.net.InetAddress.getLocalHost().getHostName();
            String browserName = System.getProperty("browser");
            String env = System.getProperty("env");

            extent.setSystemInfo("Computer Name", computerName);
            extent.setSystemInfo("browser", browserName);
            extent.setSystemInfo("Environment", env);
            extent.setSystemInfo("tester", "Mahesh");
        }
        } catch (Exception e) {
            System.out.println("Error during report initialization: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testCaseName = result.getName();
        ExtentTest test = extent.createTest(testCaseName); // Retrieve the specific ExtentTest
        test.log(Status.SKIP, "Test case skipped is: " + testCaseName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testCaseName = result.getName();
         test = extent.createTest(testCaseName); // Retrieve the specific ExtentTest
        test.log(Status.PASS, "Test case passed is: " + testCaseName);

    }

    @Override
    public void onTestFailure(ITestResult result) {
        RetryAnalyzer retryAnalyzer = (RetryAnalyzer) result.getMethod().getRetryAnalyzer(result);
        if (retryAnalyzer != null && retryAnalyzer.getRetryCount() < 2) {
            System.out.println("Retry attempt for test: " + result.getName());
            // Don't log the failure yet, since retries are in progress
            return;
        }
        // Log the final failure only
        test = extent.createTest(result.getName());
            test.log(Status.FAIL, "Test case failed after retries: " + result.getName());
    }


    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
        System.out.println("Applying RetryAnalyzer to test method: " + testMethod.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush(); // Write the report data
        } else {
            System.out.println("ExtentReports is null; cannot flush the report.");
        }
    }
}