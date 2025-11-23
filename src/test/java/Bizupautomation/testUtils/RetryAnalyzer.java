package Bizupautomation.testUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger logger = LogManager.getLogger(RetryAnalyzer.class); // Logger instance

    static {
        // Force log4j2 to initialize at class load time
        System.setProperty("log4j2.debug", "true");
        // logger.info("RetryAnalyzer class initialized ✅");
    }

    private int retryCount = 0;
    private static final int maxRetryCount = 2; // Set the max retry attempts

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            logger.info("🔄 Retrying " + result.getName() + " | Attempt " + (retryCount + 1));

            // Ensure the test is NOT marked as skipped
            result.setStatus(ITestResult.FAILURE);
            return true; // Retry the test
        }
        return false; // Stop retrying
    }
}
