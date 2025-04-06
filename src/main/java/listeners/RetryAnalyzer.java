package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {

        int MAX_RETRY_COUNT = Integer.parseInt(System.getProperty("failedRetryAttempts"));

        if (!result.isSuccess()) {
            if (retryCount < MAX_RETRY_COUNT) {
                retryCount++;
                System.out.println("Retrying test: " + result.getName() + " for the " + retryCount + " time.");
                return true;  // Retry the test
            } else {
                result.setStatus(ITestResult.FAILURE); // Final failure
            }

        } else {
            result.setStatus(ITestResult.SUCCESS); // Final Success
        }

        return false;  // No more retries
    }

    public int getRetryCount() {
        return retryCount;
    }

}