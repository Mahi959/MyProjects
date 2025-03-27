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
import java.util.HashMap;
import java.util.Map;

public class GlobalRetryListener implements IAnnotationTransformer, ITestListener {

    public ExtentSparkReporter sparkReporter;  //UI of the report
    public ExtentReports extent;  //Populate common info on the report

    //Creating TC entry in the report and update status of the test methods
    private Map<String, ExtentTest> extentTestMap = new HashMap<>(); // Map to track ExtentTest instances

    public GlobalRetryListener() {
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testCaseName = result.getName();
        try {
            // Step 1: Define the report path and check for null or empty
            String reportPath = "./src/test/reports/myReport.html";

            // Step 2: Ensure the directory exists
            File reportDir = new File(reportPath).getParentFile();
            if (!reportDir.exists() && reportDir.mkdirs()) {
                System.out.println("Directory created: " + reportDir.getAbsolutePath());
            }

            // Step 3: Initialize ExtentSparkReporter
            sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setDocumentTitle("Automation Report");
            sparkReporter.config().setReportName("Functional Testing");
            sparkReporter.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Computer Name", "Local host");
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Tester", "Mahesh");

        } catch (Exception e) {
            System.out.println("Error during report initialization: " + e.getMessage());
            e.printStackTrace();
        }

        // Create a new ExtentTest entry for each test case
        ExtentTest test = extent.createTest(testCaseName);
        extentTestMap.put(testCaseName, test); // Save the ExtentTest instance in the Map
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testCaseName = result.getName();
        ExtentTest test = extentTestMap.get(testCaseName); // Retrieve the specific ExtentTest
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
        ExtentTest test = extentTestMap.get(result.getName());
            test.log(Status.FAIL, "Test case failed after retries: " + result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testCaseName = result.getName();
        ExtentTest test = extentTestMap.get(testCaseName); // Retrieve the specific ExtentTest
            test.log(Status.SKIP, "Test case skipped is: " + testCaseName);
    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
        System.out.println("Applying RetryAnalyzer to test method: " + testMethod.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush(); // Flush the report to write all entries
    }
}