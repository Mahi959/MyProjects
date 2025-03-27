package com.rbc.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = 2;  // Retry up to 2 times

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            System.out.println("Retrying test: " + result.getName() + " for the " + retryCount + " time.");
            return true;  // Retry the test
        }
        return false;  // No more retries
    }
    public int getRetryCount() {
        return retryCount;
    }

}